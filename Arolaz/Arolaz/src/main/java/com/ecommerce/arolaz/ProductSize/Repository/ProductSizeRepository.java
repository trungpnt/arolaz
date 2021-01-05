package com.ecommerce.arolaz.ProductSize.Repository;

import com.ecommerce.arolaz.ProductSize.Model.ProductSize;
import org.bson.types.ObjectId;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductSizeRepository extends PagingAndSortingRepository<ProductSize, ObjectId>{
}
