package com.ecommerce.arolaz.Product.Controller;


import com.ecommerce.arolaz.Brand.Model.Brand;
import com.ecommerce.arolaz.Brand.Repository.BrandRepository;
import com.ecommerce.arolaz.Category.Model.Category;
import com.ecommerce.arolaz.Category.Repository.CategoryRepository;
import com.ecommerce.arolaz.Color.Model.Color;
import com.ecommerce.arolaz.Color.Repository.ColorRepository;
import com.ecommerce.arolaz.ExceptionHandlers.AdminRoleNotFoundException;
import com.ecommerce.arolaz.ExceptionHandlers.InvalidTokenException;
import com.ecommerce.arolaz.ExceptionHandlers.ProductNotFoundException;
import com.ecommerce.arolaz.Inventory.Model.Inventory;
import com.ecommerce.arolaz.Inventory.Repository.InventoryRepository;
import com.ecommerce.arolaz.Inventory.Service.InventoryService;
import com.ecommerce.arolaz.Product.Model.Product;
import com.ecommerce.arolaz.Product.Repository.ProductRepository;
import com.ecommerce.arolaz.Product.RequestResponseModels.CreateProductRequestModel;
import com.ecommerce.arolaz.Product.RequestResponseModels.ProductResponseModel;
import com.ecommerce.arolaz.Product.RequestResponseModels.ProductSizePriceQuantityColor;
import com.ecommerce.arolaz.Product.Service.ProductService;
import com.ecommerce.arolaz.ProductSize.Model.ProductSize;
import com.ecommerce.arolaz.ProductSize.Repository.ProductSizeRepository;
import com.ecommerce.arolaz.S3BucketFileHandler.ProductImgUrlResponseModel;
import com.ecommerce.arolaz.S3BucketFileHandler.Service.AWSS3Service;
import com.ecommerce.arolaz.Security.JwtProvider;
import com.ecommerce.arolaz.Size.Model.Size;
import com.ecommerce.arolaz.Size.Repository.SizeRepository;
import com.ecommerce.arolaz.utils.CustomizedPagingResponseModel;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private SizeRepository sizeRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ProductSizeRepository productSizeRepository;

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private ColorRepository colorRepository;

    @Autowired
    private AWSS3Service amsService;

    @Autowired
    private BrandRepository brandRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);



    /**
     * 1 transaction
     * 3 database persists
     * */
    @PostMapping("/product")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Product> addProduct(HttpServletRequest request, @RequestHeader(value = "Authorization") String headerVal, @RequestPart(value= "file") final MultipartFile multipartFile, @RequestParam(value="categoryName") String categoryName, @RequestParam(value="brandName") String brandName, @RequestParam(value="productSizeNames") List<String> productSizeNames, @RequestParam(value="productSizePrices") List<Double> productSizePrices, @RequestParam(value="name") String name, @RequestParam(value="availableProductColorNames") List<String> productColorNames, @RequestParam(value = "productQuantities") List<Integer> productQuantities, @RequestParam(value="description") String description){

        String token = headerVal.substring(headerVal.indexOf(" "));

        if (!jwtProvider.isValidToken(token)) {
            throw new InvalidTokenException("TOKEN IS INVALID");
        }
        String adminRole = jwtProvider.getRoles(token).get(0).getAuthority();
        if(adminRole.compareTo("ADMIN") != 0){
            throw new AdminRoleNotFoundException("TOKEN DOES NOT CONTAIN ADMIN AUTHORITY");
        }

        CreateProductRequestModel createProductRequestModel = new CreateProductRequestModel(categoryName,brandName,productSizeNames,productSizePrices,productQuantities,name,productColorNames,description);

        //Uploading img to S3 bucket and retrieve the abs path
        ProductImgUrlResponseModel productImgUrlResponseModel = amsService.uploadFile(multipartFile);
        final String imgUrl = "[" + multipartFile.getOriginalFilename() + "] uploaded successfully.";

        productImgUrlResponseModel.setFileName(imgUrl);

        Product persistProduct = modelMapper.map(createProductRequestModel, Product.class);
        //Build ProductSizeQuantityColors list for Product model
        List<ProductSizePriceQuantityColor> productSizePriceQuantityColors = extractDataAndBuildList(createProductRequestModel);
        persistProduct.setProductSizePriceQuantityColors(productSizePriceQuantityColors);

        //Retrieve BrandId
        Optional<Brand> foundBrand = brandRepository.findByBrandName(createProductRequestModel.getBrandName());
        persistProduct.setBrandId(foundBrand.get().getBrandId().toString());

        //Retrieve CategoryId
        Optional<Category> foundCategory = categoryRepository.findByCategoryName(createProductRequestModel.getCategoryName());
        persistProduct.setCategoryId(foundCategory.get().getCategoryId().toString());

        //Set imgUrl
        persistProduct.setImgUrl(productImgUrlResponseModel.getImgUrl());

        Product afterSaved = productRepository.save(persistProduct);

        String productId = afterSaved.getProductId().toString();


        /**
         * 1st -- Persist Product Size
         * 2nd -- Persist Inventory
         * */
        List<ProductSizePriceQuantityColor> listToExtract = afterSaved.getProductSizePriceQuantityColors();

        for(int i = 0, n = listToExtract.size(); i < n; i++){
            //1st
            ProductSize persistProductSize = new ProductSize(listToExtract.get(i).getPrice(),listToExtract.get(i).getSizeName(),productId);
            productSizeRepository.save(persistProductSize);

            //2nd
            Optional<Size> foundSize = sizeRepository.findBySizeName(listToExtract.get(i).getSizeName());
            String sizeId = foundSize.get().getSizeId().toString();

            Optional<Color> foundColor = colorRepository.findByColorName(listToExtract.get(i).getColorName());
            String colorId = foundColor.get().getColorId().toString();

            Inventory persistInventory = new Inventory(productId,sizeId,colorId,listToExtract.get(i).getQuantity());
            inventoryRepository.save(persistInventory);
        }

        /*
        Persist inventory
        **/

        return new ResponseEntity<>(persistProduct,HttpStatus.CREATED);
    }

    List<ProductSizePriceQuantityColor> extractDataAndBuildList(CreateProductRequestModel createProductRequestModel){
        List<ProductSizePriceQuantityColor> productSizePriceQuantityColors = new ArrayList<>();
        for(int i = 0, n = createProductRequestModel.getAvailableProductColorNames().size(); i < n; i++){
            productSizePriceQuantityColors.add(new ProductSizePriceQuantityColor(createProductRequestModel.getProductSizeNames().get(i),createProductRequestModel.getProductSizePrices().get(i),createProductRequestModel.getAvailableProductColorNames().get(i),createProductRequestModel.getQuantities().get(i)));
        }
        return productSizePriceQuantityColors;
    }


    @PutMapping("/product")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateProduct(@RequestBody @Valid CreateProductRequestModel createProductRequestModel){
        Product product = modelMapper.map(createProductRequestModel, Product.class);
        productService.addProduct(product);
    }

    @DeleteMapping("/product/{productId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteProductById(@PathVariable(value = "productId") String productId){
        ObjectId proId = new ObjectId(productId);
        Optional<Product> product = productService.findByProductId(proId);
        if(!product.isPresent()){
            throw new ProductNotFoundException(String.format("Product with given {proId} not found",productId));
        }


        productRepository.delete(product.get());
    }

    @GetMapping(path = "/product")
    public CustomizedPagingResponseModel<ProductResponseModel> getProducts(
            @RequestParam("page") Integer page,
            @RequestParam("rows") Integer rows,
            @RequestParam(value = "categoryId",required = false) String categoryId, Pageable pageable) {
        Page<Product> productPage;
        //
        if(categoryId == null){
            productPage = productService.findAllProducts(pageable);
        }
        else{
            productPage = productService.findByCategoryId(categoryId,pageable);
        }

        List<ProductResponseModel> productResponseModelList = productPage.getContent().stream().map(
                product -> toDto(product)).collect(Collectors.toList());
        CustomizedPagingResponseModel<ProductResponseModel> customizedPagingResponseModel = new CustomizedPagingResponseModel<>();

        customizedPagingResponseModel.setPagingData(productResponseModelList);
        customizedPagingResponseModel.setTotalPage(productPage.getTotalPages());
        return customizedPagingResponseModel;
    }

    private ProductResponseModel toDto(Product product) {
        String productId = product.getProductId().toString();
        return new ProductResponseModel(productId,product.getProductName(),product.getCategoryName(),product.getImgUrl(),product.getBrandName(),product.getDescription(),product.getProductSizePriceQuantityColors());

    }

}
