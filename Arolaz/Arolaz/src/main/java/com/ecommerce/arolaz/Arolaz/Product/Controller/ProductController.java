package com.ecommerce.arolaz.Arolaz.Product.Controller;


import com.ecommerce.arolaz.Arolaz.Category.Repository.CategoryRepository;
import com.ecommerce.arolaz.Arolaz.Product.Model.Product;
import com.ecommerce.arolaz.Arolaz.Product.Repository.ProductRepository;
import com.ecommerce.arolaz.Arolaz.Product.RequestResponseModels.CreateProductRequestModel;
import com.ecommerce.arolaz.Arolaz.Product.RequestResponseModels.ProductResponseModel;
import com.ecommerce.arolaz.Arolaz.Product.Service.ProductService;
import com.ecommerce.arolaz.Arolaz.ProductSize.Repository.ProductSizeRepository;
import com.ecommerce.arolaz.Arolaz.S3BucketFileHandler.ProductImgUrlResponseModel;
import com.ecommerce.arolaz.Arolaz.S3BucketFileHandler.Service.AWSS3Service;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ProductSizeRepository productSizeRepository;

    @Autowired
    private AWSS3Service amsService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

//    @GetMapping(path = "/product/{productId}")
//    public ResponseEntity<ProductResponseModel> getSingleProduct(@PathVariable(value = "productId") ObjectId productId) {
//        Optional<Product> product = productRepository.findById(productId);
//
//        ProductResponseModel productResponseModel = toDto(product.get());
//        return new ResponseEntity<ProductResponseModel>(productResponseModel,HttpStatus.OK);
//    }

//    @GetMapping(path = "/product")
//    public CustomizedPagingResponseModel<ProductResponseModel> getProducts(
//            @RequestParam("page") Integer page,
//            @RequestParam("rows") Integer rows,
//            @RequestParam(value = "categoryId",required = false) ObjectId categoryId, Pageable pageable) {
//        Page<Product> productPage;
//        ObjectId catId = new ObjectId(categoryId.toString());
//        if(categoryId == null){
//            productPage = productService.findAllProducts(pageable);
//        }
//        else{
//            productPage = productService.findByCategoryId(catId,pageable);
//        }
//
//        List<ProductResponseModel> productResponseModelList = productPage.getContent().stream().map(
//                product -> toDto(product)).collect(Collectors.toList());
//        CustomizedPagingResponseModel<ProductResponseModel> customizedPagingResponseModel = new CustomizedPagingResponseModel<>();
//
//        customizedPagingResponseModel.setPagingData(productResponseModelList);
//        customizedPagingResponseModel.setTotalPage(productPage.getTotalPages());
//        return customizedPagingResponseModel;
//    }

//    @GetMapping(path = "/product")
//    public CustomizedPagingResponseModel<ProductResponseModel> getProducts(
//            @RequestParam("page") Integer page,
//            @RequestParam("rows") Integer rows,
//            @RequestParam(value = "categoryId",required = false) String categoryId, Pageable pageable) {
//        Page<Product> productPage;
//        if(categoryId == null){
//            productPage = productService.findAllProducts(pageable);
//        }
//        else{
//            productPage = productService.findByCategoryId(categoryId,pageable);
//        }
//
//        List<ProductResponseModel> productResponseModelList = productPage.getContent().stream().map(
//                product -> toDto(product)).collect(Collectors.toList());
//        CustomizedPagingResponseModel<ProductResponseModel> customizedPagingResponseModel = new CustomizedPagingResponseModel<>();
//
//        customizedPagingResponseModel.setPagingData(productResponseModelList);
//        customizedPagingResponseModel.setTotalPage(productPage.getTotalPages());
//        return customizedPagingResponseModel;
//    }
//
//    private ProductResponseModel toDto(Product product) {
//        ObjectId categoryId = new ObjectId(product.getCategoryId().toString());
//        Optional<Category> categoryFound = categoryRepository.findById(categoryId);
//        String categoryName = categoryFound.get().getCategoryName();
//        return new ProductResponseModel(product.getProductId().toString(),product.getName(),product.getPrice(),product.getImgUrl(),product.getDescription(),product.getCategoryId().toString(),categoryName);
//    }

    private CreateProductRequestModel toCreateProduct(String catId, String brandId, String colorId, String productSizeId, Double price, String name, String description){
        return new CreateProductRequestModel(catId,brandId,colorId,productSizeId,price,name,description);
    }
    private ProductResponseModel toProductResponse(Product persistProduct){
       return new ProductResponseModel(persistProduct.getProductId().toString(),persistProduct.getName(), persistProduct.getCategoryId(), persistProduct.getImgUrl(), persistProduct.getBrandId(), persistProduct.getDescription());
    }
    //Conversion bug
//    @PostMapping("/product")
//    @ResponseStatus(HttpStatus.CREATED)
//    @PreAuthorize("hasAuthority('ADMIN')")
//    public void addProduct( @RequestPart(value= "file") final MultipartFile multipartFile, @RequestParam(value="categoryId") String categoryId, @RequestParam(value="brandId") String brandId,@RequestParam(value="colorId") String colorId,@RequestParam(value="productSizeId") String productSizeId, @RequestParam(value="price") Double price, @RequestParam(value="name") String name, @RequestParam(value="description") String description ){
//
//        CreateProductRequestModel createProductRequestModel = toCreateProduct(categoryId,brandId,colorId,productSizeId,price,name,description);
//
//        ResponseEntity<Product> product = proxy.uploadFile(multipartFile);
//
//        Product persistProduct = modelMapper.map(createProductRequestModel, Product.class);
//        productService.addProduct(persistProduct);
//
//        //Get productId
//        //Persist product_color
//    }

    @PostMapping("/product")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ProductResponseModel> addProduct(@RequestPart(value= "file") final MultipartFile multipartFile, @RequestParam(value="categoryId") String categoryId, @RequestParam(value="brandId") String brandId, @RequestParam(value="colorId") String colorId, @RequestParam(value="productSizeId") String productSizeId, @RequestParam(value="price") Double price, @RequestParam(value="name") String name, @RequestParam(value="description") String description ){

        CreateProductRequestModel createProductRequestModel = toCreateProduct(categoryId,brandId,colorId,productSizeId,price,name,description);

        ProductImgUrlResponseModel productImgUrlResponseModel = amsService.uploadFile(multipartFile);
        final String imgUrl = "[" + multipartFile.getOriginalFilename() + "] uploaded successfully.";

        productImgUrlResponseModel.setFileName(imgUrl);

        Product persistProduct = modelMapper.map(createProductRequestModel, Product.class);
        persistProduct.setImgUrl(productImgUrlResponseModel.getImgUrl());
        productService.addProduct(persistProduct);

        ProductResponseModel productResponseModel = toProductResponse(persistProduct);

        return new ResponseEntity<>(productResponseModel,HttpStatus.CREATED);
        //Get productId
        //Persist product_color
        //Persist product_size
        //Persist inventory

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
    public void deleteProduct(@PathVariable(value = "productId") String productId){
        ObjectId proId = new ObjectId(productId);
        Optional<Product> product = productService.findByProductId(proId);
        productRepository.delete(product.get());
    }

//    private ResponseEntity<ProductListResponseModel> buildProductListResponse(List<Product> productList) {
//        ProductListResponseModel productListResponseModel = new ProductListResponseModel();
//        if(productList != null){
//            productList.forEach(item -> productListResponseModel.addProductsItem(modelMapper.map(item, ProductResponseModel.class)));
//        }
//        return new ResponseEntity<>(productListResponseModel, HttpStatus.OK);
//    }

}
