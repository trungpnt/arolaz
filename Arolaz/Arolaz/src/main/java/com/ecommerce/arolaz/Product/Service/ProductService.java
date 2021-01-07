package com.ecommerce.arolaz.Product.Service;

import com.ecommerce.arolaz.Product.Model.Product;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ProductService {
    Page<Product> findByCategoryId(String categoryId, Pageable pageable);
    Product addProduct(Product product);
    Page<Product> findAllProducts(Pageable pageable);
    Optional<Product> findByProductId(ObjectId productId);
    void deleteProductById(ObjectId productId);
}
