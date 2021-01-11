package com.ecommerce.arolaz.Product.Service;

import com.ecommerce.arolaz.Product.Model.Product;
import com.ecommerce.arolaz.Product.Model.ProductPage;
import com.ecommerce.arolaz.Product.Model.ProductSearchCriteria;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;
import java.util.Optional;

public interface ProductService {
    Page<Product> findByCategoryId(String categoryId, Pageable pageable);
    Product addProduct(Product product);
    Page<Product> findAllProducts(Pageable pageable);
    Optional<Product> findByProductId(ObjectId productId);
    void deleteProductById(ObjectId productId);
    public Page<Product> getCriteriaProductV1(ProductSearchCriteria productSearchCriteria);
}
