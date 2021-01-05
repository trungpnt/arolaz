package com.ecommerce.arolaz.Arolaz.Brand.Repository;


import com.ecommerce.arolaz.Arolaz.Brand.Model.Brand;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends MongoRepository<Brand, ObjectId> {
}
