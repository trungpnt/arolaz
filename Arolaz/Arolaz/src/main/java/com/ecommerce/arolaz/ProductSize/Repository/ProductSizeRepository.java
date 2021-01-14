package com.ecommerce.arolaz.ProductSize.Repository;

import com.ecommerce.arolaz.ProductSize.Model.ProductSize;
import org.bson.types.ObjectId;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductSizeRepository extends PagingAndSortingRepository<ProductSize, ObjectId>{
    Optional<ProductSize> findByProductIdAndSizeNameAndPrice(String proId, String sizeName, Double price);
    List<ProductSize> findByProductId(String productId);
    Optional<ProductSize> findByProductIdAndSizeName(String productId, String sizeName);
    void deleteByProductId(String productId);
    List<ProductSize> findAll();
}
