package com.ecommerce.arolaz.ProductSize.Service;

import com.ecommerce.arolaz.utils.ExceptionHandlers.ProductSizeNotFoundException;
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
    public Optional<ProductSize> findByProductIdAndSizeNameAndPrice(String proId, String sizeName, Double price) {
        Optional<ProductSize> find = productSizeRepository.findByProductIdAndSizeNameAndPrice(proId, sizeName, price);
        if(!find.isPresent()){
            throw new ProductSizeNotFoundException(String.format("product size with '%s','%s','%f' not found!",proId,sizeName,price));
        }
        return find;
    }

    @Override
    public Optional<ProductSize> findByProductIdAndSizeName(String proId, String sizeName) {
        Optional<ProductSize> find = productSizeRepository.findByProductIdAndSizeName(proId, sizeName);
        if (!find.isPresent()){
            throw new ProductSizeNotFoundException(String.format("product size with '%s','%s' not found",proId,sizeName));
        }
        return find;
    }

}
