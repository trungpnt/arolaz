package com.ecommerce.arolaz.Arolaz.Product.Service;


import com.amazonaws.services.alexaforbusiness.model.NotFoundException;
import com.ecommerce.arolaz.Arolaz.Product.Model.Product;
import com.ecommerce.arolaz.Arolaz.Product.Repository.ProductRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public Page<Product> findByCategoryId(String categoryId, Pageable pageable) {
        return productRepository.findByCategoryId(categoryId,pageable);
    }

    @Override
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    //@Cacheable
    public Page<Product> findAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public Optional<Product> findByProductId(ObjectId productId) {
        Optional<Product> product = productRepository.findByProductId(productId);
        if(!product.isPresent()){
            throw new NotFoundException("Product not found !");
        }
        return product;
    }

}
