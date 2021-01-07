package com.ecommerce.arolaz.ProductSize.Service;

import com.ecommerce.arolaz.ProductSize.Model.ProductSize;
import com.ecommerce.arolaz.ProductSize.Repository.ProductSizeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductSizeServiceImpl implements ProductSizeService{
    @Autowired
    private ProductSizeRepository productSizeRepository;

    @Override
    public ProductSize addNewProductSize(ProductSize productSize) {
        return productSizeRepository.save(productSize);
    }

    @Override
    public Optional<ProductSize> findByGivenProperties(String proId, String sizeName, Double price) {
        Optional<ProductSize> find = productSizeRepository.findByProductIdAndSizeNameAndPrice(proId, sizeName, price);
        return find;
    }

//    private ProductSizeResponseModel toProductSizeResponseModel(ProductSize productSize){
//        return new ProductSizeResponseModel(productSize.getProductSizeId().toString(),productSize.getProductId(),productSize.getSizeName(),productSize.getPrice());
//    }
}
