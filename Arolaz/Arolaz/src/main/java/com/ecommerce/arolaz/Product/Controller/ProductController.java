package com.ecommerce.arolaz.Product.Controller;


import com.ecommerce.arolaz.Brand.Model.Brand;
import com.ecommerce.arolaz.Brand.Service.BrandService;
import com.ecommerce.arolaz.Category.Model.Category;
import com.ecommerce.arolaz.Category.Service.CategoryService;
import com.ecommerce.arolaz.Color.Model.Color;
import com.ecommerce.arolaz.Color.Service.ColorService;
import com.ecommerce.arolaz.Inventory.Model.Inventory;
import com.ecommerce.arolaz.Inventory.Service.InventoryService;
import com.ecommerce.arolaz.Product.Model.Product;
import com.ecommerce.arolaz.Product.RequestResponseModels.CreateProductRequestModel;
import com.ecommerce.arolaz.Product.RequestResponseModels.ProductResponseModel;
import com.ecommerce.arolaz.Product.RequestResponseModels.ProductSizePriceQuantityColor;
import com.ecommerce.arolaz.Product.Service.ProductService;
import com.ecommerce.arolaz.ProductSize.Model.ProductSize;
import com.ecommerce.arolaz.ProductSize.Service.ProductSizeService;
import com.ecommerce.arolaz.utils.S3BucketFileHandler.ProductImgUrlResponseModel;
import com.ecommerce.arolaz.utils.S3BucketFileHandler.Service.AWSS3Service;
import com.ecommerce.arolaz.Size.Model.Size;
import com.ecommerce.arolaz.Size.Service.SizeService;
import com.ecommerce.arolaz.utils.CustomizedPagingResponseModel;
import com.ecommerce.arolaz.utils.TokenValidator;
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
    private SizeService sizeService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ProductSizeService productSizeService;

    @Autowired
    private ColorService colorService;

    @Autowired
    private TokenValidator tokenValidator;

    @Autowired
    private AWSS3Service amsService;

    @Autowired
    private BrandService brandService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    /**
     *
     * --Persist new Product
     * --Persist new ProductSize
     * --Persist new Inventory
     * */
    @PostMapping("/product")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Product> addProduct(HttpServletRequest request, @RequestHeader(value = "Authorization") String headerVal, @RequestPart(value= "file") final MultipartFile multipartFile, @RequestParam(value="categoryName") String categoryName, @RequestParam(value="brandName") String brandName, @RequestParam(value="productSizeNames") List<String> productSizeNames, @RequestParam(value="productSizePrices") List<Double> productSizePrices, @RequestParam(value="name") String name, @RequestParam(value="availableProductColorNames") List<String> productColorNames, @RequestParam(value = "productQuantities") List<Integer> productQuantities, @RequestParam(value="description") String description){

        String token = headerVal.substring(headerVal.indexOf(" "));
        tokenValidator.validateTokenAdminAuthorization(token);

        /**
         * Manually map all JSON values to CreateProductRequestModel class
         * @param listOfArgs
         * @return createProductRequestModel
         * */
        CreateProductRequestModel createProductRequestModel = new CreateProductRequestModel(categoryName,brandName,productSizeNames,productSizePrices,productQuantities,name,productColorNames,description);

        /**
         * Upload image file to S3 bucket and combine ImgUrl for product
         * @param multipartFile
         * @return imgUrl (https://s3-bucket)
         * */
        ProductImgUrlResponseModel productImgUrlResponseModel = amsService.uploadFile(multipartFile);
        final String imgUrl = "[" + multipartFile.getOriginalFilename() + "] uploaded successfully.";
        productImgUrlResponseModel.setFileName(imgUrl);

        Product persistProduct = modelMapper.map(createProductRequestModel, Product.class);
        persistProduct.setImgUrl(productImgUrlResponseModel.getImgUrl());

        /**
         * Build ProductSizeQuantityColors for Product model
         * @param listOfArgs
         * @return Product with ProductSizePriceQuantityColor
         * */
        List<ProductSizePriceQuantityColor> productSizePriceQuantityColors = extractDataAndBuildList(createProductRequestModel);
        persistProduct.setProductSizePriceQuantityColors(productSizePriceQuantityColors);

        /**
         * Retrieve brandId for product
         * @param brandName
         * @return brandId
         * */
        Optional<Brand> foundBrand = brandService.findByBrandName(createProductRequestModel.getBrandName());
        persistProduct.setBrandId(foundBrand.get().getBrandId().toString());

        /**
         * Retrieve CategoryId for product
         * @param categoryName
         * @return categoryId
         * */
        Optional<Category> foundCategory = categoryService.findByCategoryName(createProductRequestModel.getCategoryName());
        persistProduct.setCategoryId(foundCategory.get().getCategoryId().toString());


        Product afterSaved = productService.addProduct(persistProduct);

        String productId = afterSaved.getProductId().toString();

        /**
         * Traverse through each JSON array elements
         * 1st -- Persist Product Size
         * 2nd -- Persist Inventory
         * */
        List<ProductSizePriceQuantityColor> listToExtract = afterSaved.getProductSizePriceQuantityColors();

        for(int i = 0, n = listToExtract.size(); i < n; i++){
            /*
            *Persist product size
            **/
            ProductSize persistProductSize = new ProductSize(listToExtract.get(i).getPrice(),listToExtract.get(i).getSizeName(),productId);
            productSizeService.addNewProductSize(persistProductSize);

            /*
             *Persist inventory
             **/
            Optional<Size> foundSize = sizeService.findBySizeName(listToExtract.get(i).getSizeName());
            String sizeId = foundSize.get().getSizeId().toString();

            Optional<Color> foundColor = colorService.findByColorName(listToExtract.get(i).getColorName());
            String colorId = foundColor.get().getColorId().toString();

            Inventory persistInventory = new Inventory(productId,sizeId,colorId,listToExtract.get(i).getQuantity());
            inventoryService.addNewInventory(persistInventory);
        }

        return new ResponseEntity<>(persistProduct,HttpStatus.CREATED);
    }


    /**
     * Manually map each corresponding JSON fields
     * @param createProductRequestModel
     * @return List<ProductSizePriceQuantityColor>
     * */
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
    public ResponseEntity<ProductResponseModel> updateProduct(@RequestBody @Valid CreateProductRequestModel createProductRequestModel,HttpServletRequest request, @RequestHeader(value = "Authorization") String headerVal){
        String token = headerVal.substring(headerVal.indexOf(" "));
        tokenValidator.validateTokenAdminAuthorization(token);

        Product product = modelMapper.map(createProductRequestModel, Product.class);
        Product updatedProduct = productService.addProduct(product);

        return new ResponseEntity<>(toDto(updatedProduct),HttpStatus.CREATED);
    }

    @DeleteMapping("/product/{productId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteProductById(@PathVariable(value = "productId") String productId, HttpServletRequest request, @RequestHeader(value = "Authorization") String headerVal){
        String token = headerVal.substring(headerVal.indexOf(" "));
        tokenValidator.validateTokenAdminAuthorization(token);

        ObjectId proId = new ObjectId(productId);
        Optional<Product> product = productService.findByProductId(proId);
        productService.deleteProductById(product.get().getProductId());
    }

    /**
     * Fetch a list of product with optionally-included CategoryId
     * */
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
