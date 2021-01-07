package com.ecommerce.arolaz.Product.Service;

import com.ecommerce.arolaz.utils.ExceptionHandlers.ProductNotFoundException;
import com.ecommerce.arolaz.Product.Model.Product;
import com.ecommerce.arolaz.Product.Repository.ProductRepository;
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
            throw new ProductNotFoundException(String.format("Product with '%s' not found !",productId.toString()));
        }
        return product;
    }

    @Override
    public void deleteProductById(ObjectId productId) {
        Optional<Product> product = findByProductId(productId);
        if(product.isPresent()){
            productRepository.delete(product.get());
        }
    }

}
