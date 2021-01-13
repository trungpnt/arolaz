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

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ProductSizeController {
    @Autowired
    private ProductSizeService service;

    @Autowired
    private ModelMapper modelMapper;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/product-size")
    public ResponseEntity<ProductSizeResponseModel> addNewProductSize(CreateProductSizeRequestModel createProductSizeRequestModel){
        ProductSize persistProductSize = modelMapper.map(createProductSizeRequestModel,ProductSize.class);

        Optional<ProductSize> foundProductSize = service.findByProductIdAndSizeNameAndPrice(createProductSizeRequestModel.getProductId(), createProductSizeRequestModel.getSizeName(), createProductSizeRequestModel.getPrice());

        service.addNewProductSize(persistProductSize);

        return new ResponseEntity<>(toProductSizeResponseModel(persistProductSize), HttpStatus.CREATED);
    }

    private ProductSizeResponseModel toProductSizeResponseModel(ProductSize productSize){
        return new ProductSizeResponseModel(productSize.getProductId().toString(), productSize.getProductId(), productSize.getSizeName(), productSize.getPrice());
    }

}
