package com.ecommerce.arolaz.Product.Service;

import com.ecommerce.arolaz.Product.Model.Product;
import com.ecommerce.arolaz.Product.Model.ProductDynamicQuery;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    Page<Product> findByCategoryId(String categoryId, Pageable pageable);
    Product addProduct(Product product);
    Page<Product> findAllProducts(Pageable pageable);
    Optional<Product> findByProductId(ObjectId productId);
    void deleteProductById(ObjectId productId);
    public Page<Product> getCriteriaProductV1(ProductDynamicQuery productDynamicQuery);
    List<Product> query(ProductDynamicQuery productDynamicQuery);
}
