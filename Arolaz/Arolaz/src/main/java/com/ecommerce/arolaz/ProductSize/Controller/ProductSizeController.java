package com.ecommerce.arolaz.ProductSize.Controller;

import com.ecommerce.arolaz.ProductSize.Model.ProductSize;
import com.ecommerce.arolaz.ProductSize.Repository.ProductSizeRepository;
import com.ecommerce.arolaz.ProductSize.RequestResponseModels.CreateProductSizeRequestModel;
import com.ecommerce.arolaz.ProductSize.RequestResponseModels.ProductSizeResponseModel;
import com.ecommerce.arolaz.ProductSize.Service.ProductSizeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ProductSizeController {
    @Autowired
    private ProductSizeService service;

    @GetMapping("/product-size")
    public ResponseEntity<List<ProductSizeResponseModel>> getAllProductSizes(){
        List<ProductSize> allProductSizes = service.findAll();
        return new ResponseEntity<>(buildList(allProductSizes),HttpStatus.OK);
    }

    private ProductSizeResponseModel toProductSizeResponseModel(ProductSize productSize){
        return new ProductSizeResponseModel(productSize.getProductId().toString(), productSize.getProductId(), productSize.getSizeName(), productSize.getPrice());
    }

    private List<ProductSizeResponseModel> buildList(List<ProductSize> productSizes){
        return productSizes.stream().map(eachProductSize -> toProductSizeResponseModel(eachProductSize)).collect(Collectors.toList());
    }

}
