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
import com.ecommerce.arolaz.Product.Model.ProductDynamicQuery;
import com.ecommerce.arolaz.Product.RequestResponseModels.CreateProductRequestModel;
import com.ecommerce.arolaz.Product.RequestResponseModels.ProductResponseModel;
import com.ecommerce.arolaz.Product.RequestResponseModels.ProductSizePriceQuantityColor;
import com.ecommerce.arolaz.Product.RequestResponseModels.UpdateProductRequestModel;
import com.ecommerce.arolaz.Product.Service.ProductService;
import com.ecommerce.arolaz.ProductSize.Model.ProductSize;
import com.ecommerce.arolaz.ProductSize.Service.ProductSizeService;
import com.ecommerce.arolaz.Size.Model.Size;
import com.ecommerce.arolaz.Size.Service.SizeService;
import com.ecommerce.arolaz.Utils.CustomizedPagingResponseModel;
import com.ecommerce.arolaz.Utils.ExceptionHandlers.ProductIdNotProvidedException;
import com.ecommerce.arolaz.Utils.S3BucketFileHandler.ProductImgUrlResponseModel;
import com.ecommerce.arolaz.Utils.S3BucketFileHandler.Service.AWSS3Service;
import com.ecommerce.arolaz.Utils.TokenValidator;
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
    @PostMapping("/products")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ProductResponseModel> addProduct(HttpServletRequest request, @RequestHeader(value = "Authorization") String headerVal, @RequestPart(value= "file") final MultipartFile multipartFile, @RequestParam(value="categoryName") String categoryName, @RequestParam(value="brandName") String brandName, @RequestParam(value="productSizeNames") List<String> productSizeNames, @RequestParam(value="productSizePrices") List<Double> productSizePrices, @RequestParam(value="name") String name, @RequestParam(value="availableProductColorNames") List<String> productColorNames, @RequestParam(value = "productQuantities") List<Integer> productQuantities, @RequestParam(value="description") String description){

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

            /**
             * Assigning the starting price, color, size for this product
             * */
            if(afterSaved.getBasicSmallSizePrice() == null){
                afterSaved.setBasicSmallSizePrice(listToExtract.get(i).getPrice());
            }

            if(afterSaved.getBasicColorName() == null){
                afterSaved.setBasicColorName(listToExtract.get(i).getColorName());
            }

            if(afterSaved.getBasicSizeName() == null){
                afterSaved.setBasicSizeName(listToExtract.get(i).getSizeName());
            }
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

        productService.addProduct(afterSaved);

        return new ResponseEntity<>(toDto(afterSaved),HttpStatus.CREATED);
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

    List<ProductSizePriceQuantityColor> extractDataAndBuildList(UpdateProductRequestModel updateProductRequestModel){
        List<ProductSizePriceQuantityColor> productSizePriceQuantityColors = new ArrayList<>();
        for(int i = 0, n = updateProductRequestModel.getAvailableProductColorNames().size(); i < n; i++){
            productSizePriceQuantityColors.add(new ProductSizePriceQuantityColor(updateProductRequestModel.getProductSizeNames().get(i),updateProductRequestModel.getProductSizePrices().get(i),updateProductRequestModel.getAvailableProductColorNames().get(i),updateProductRequestModel.getQuantities().get(i)));
        }
        return productSizePriceQuantityColors;
    }

    /**
     * Should have declared required = false for each request param
     * */
    @PutMapping("/products/{productId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<ProductResponseModel> updateProduct(@RequestHeader(value = "Authorization") String headerVal, @PathVariable(value = "productId") String productId, @RequestPart(value = "file", required = false) final MultipartFile multipartFile, @RequestParam(value = "categoryName", required = false) String categoryName, @RequestParam(value = "brandName", required = false) String brandName, @RequestParam(value = "productSizeNames") List<String> productSizeNames, @RequestParam(value = "productSizePrices") List<Double> productSizePrices, @RequestParam(value = "name", required = false) String name, @RequestParam(value = "availableProductColorNames") List<String> productColorNames, @RequestParam(value = "productQuantities") List<Integer> productQuantities, @RequestParam(value = "description", required = false) String description){
        String token = headerVal.substring(headerVal.indexOf(" "));
        tokenValidator.validateTokenAdminAuthorization(token);

        UpdateProductRequestModel updateProductRequestModel = new UpdateProductRequestModel(productId,categoryName,brandName,productSizeNames,productSizePrices,productQuantities,name,productColorNames,description);

        if(productId == null){
            throw new ProductIdNotProvidedException("PLEASE PROVIDE PRODUCT_ID");
        }

        /**
         * Finding product with proId
         * */
        Optional<Product> toUpdateProduct = productService.findByProductId(new ObjectId(productId));

        ProductImgUrlResponseModel productImgUrlResponseModel = new ProductImgUrlResponseModel();
        if(multipartFile != null){
            /**
             * Upload image file to S3 bucket and combine ImgUrl for product
             * @param multipartFile
             * @return imgUrl (https://s3-bucket)
             * */
            productImgUrlResponseModel = amsService.uploadFile(multipartFile);
            final String imgUrl = "[" + multipartFile.getOriginalFilename() + "] uploaded successfully.";
            productImgUrlResponseModel.setFileName(imgUrl);
        }
        toUpdateProduct.get().setImgUrl(productImgUrlResponseModel.getImgUrl());

        /**
         * Delete records in Inventory and ProductSize with corresponding productId
         * */
        productSizeService.deleteByProductId(productId);
        inventoryService.deleteByProductId(productId);

        if(name != null){
            toUpdateProduct.get().setProductName(name);
        }

        if(categoryName != null){
            /**
             * Retrieve CategoryId for product
             * @param categoryName
             * @return categoryId
             * */
            Optional<Category> foundCategory = categoryService.findByCategoryName(categoryName);
            toUpdateProduct.get().setCategoryId(foundCategory.get().getCategoryId().toString());
            toUpdateProduct.get().setCategoryName(categoryName);
        }

        if(brandName != null){
            /**
             * Retrieve brandId for product
             * @param brandName
             * @return brandId
             * */
            Optional<Brand> foundBrand = brandService.findByBrandName(brandName);
            toUpdateProduct.get().setBrandId(foundBrand.get().getBrandId().toString());
            toUpdateProduct.get().setBrandName(brandName);
        }

        if(description != null){
            toUpdateProduct.get().setDescription(description);
        }

        /**
         * Build ProductSizeQuantityColors for Product model
         * @param listOfArgs
         * @return Product with ProductSizePriceQuantityColor
         * */
        List<ProductSizePriceQuantityColor> productSizePriceQuantityColors = extractDataAndBuildList(updateProductRequestModel);
        toUpdateProduct.get().setProductSizePriceQuantityColors(productSizePriceQuantityColors);
        toUpdateProduct.get().setBasicColorName(productColorNames.get(0));
        toUpdateProduct.get().setBasicSmallSizePrice(productSizePrices.get(0));
        Product afterSaved = productService.addProduct(toUpdateProduct.get());

        String newProductId = afterSaved.getProductId().toString();

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

            if(toUpdateProduct.get().getBasicSmallSizePrice() == null){
                toUpdateProduct.get().setBasicSmallSizePrice(listToExtract.get(i).getPrice());
            }

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

        return new ResponseEntity<>(toDto(toUpdateProduct.get()),HttpStatus.CREATED);
    }

    @DeleteMapping("/products/{productId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<String> deleteProductById(@PathVariable(value = "productId") String productId, HttpServletRequest request, @RequestHeader(value = "Authorization") String headerVal){
        String token = headerVal.substring(headerVal.indexOf(" "));
        tokenValidator.validateTokenAdminAuthorization(token);

        Optional<Product> product = productService.findByProductId(new ObjectId(productId));

        productSizeService.deleteByProductId(productId);
        inventoryService.deleteByProductId(productId);
        productService.deleteProductById(new ObjectId(productId));

        return new ResponseEntity<>("PRODUCT DELETED",HttpStatus.OK);
    }

    /**
     * Fetch a list of product with optionally-included CategoryId
     * */
    @GetMapping(path = "/products")
    @ResponseStatus(HttpStatus.OK)
    public CustomizedPagingResponseModel<ProductResponseModel> getAllProducts(
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

    @GetMapping(path = "/products/criteria/v1")
    @ResponseStatus(HttpStatus.OK)
    public CustomizedPagingResponseModel<ProductResponseModel> getProducts(@RequestParam(value = "name",required = false) /*Map<String,String> filters*/ String productName, @RequestParam(value = "color",required = false) String colorName, @RequestParam(value = "brand",required = false) String brandName, @RequestParam(value = "price",required = false) Double productPrice, @RequestParam(value="sortBy",required = false) String sortBy, @RequestParam("page") Integer page, @RequestParam("rows") Integer rows, Pageable pageable ){

        ProductDynamicQuery productDynamicQuery = new ProductDynamicQuery();

        if(sortBy != null){
            productDynamicQuery.setSortBy(sortBy);
        }

        if(productName != null){
            productDynamicQuery.setProductName(productName);
        }

        if(colorName != null){
            productDynamicQuery.setColorName(colorName);
        }

        if(brandName != null){
            productDynamicQuery.setBrandName(brandName);
        }

        if(productPrice != null){
            productDynamicQuery.setPrice(productPrice);
        }

        Page<Product> productPage = productService.getCriteriaProductV1(productDynamicQuery);

        List<ProductResponseModel> productResponseModelList = productPage.getContent().stream().map(
                product -> toDto(product)).collect(Collectors.toList());
        CustomizedPagingResponseModel<ProductResponseModel> customizedPagingResponseModel = new CustomizedPagingResponseModel<>();

        customizedPagingResponseModel.setPagingData(productResponseModelList);
        customizedPagingResponseModel.setTotalPage(productPage.getTotalPages());

        return customizedPagingResponseModel;
    }

    private ProductResponseModel toDto(Product product) {
        String productId = product.getProductId().toString();
        return new ProductResponseModel(productId,product.getProductName(),product.getCategoryName(),product.getImgUrl(),product.getBrandName(), product.getBasicSmallSizePrice(),product.getDescription(),product.getBasicColorName(),product.getBasicSizeName(), product.getProductSizePriceQuantityColors());

    }

}
