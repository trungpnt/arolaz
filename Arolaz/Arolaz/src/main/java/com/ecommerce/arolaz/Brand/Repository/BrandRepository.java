package com.ecommerce.arolaz.Brand.Repository;


import com.ecommerce.arolaz.Brand.Model.Brand;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BrandRepository extends MongoRepository<Brand, ObjectId> {
    Optional<Brand> findByBrandName(String brandName);
    boolean existsByBrandName(String brandName);

}
