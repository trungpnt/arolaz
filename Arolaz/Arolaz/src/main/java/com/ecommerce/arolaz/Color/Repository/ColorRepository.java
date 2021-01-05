package com.ecommerce.arolaz.Color.Repository;


import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColorRepository extends MongoRepository<ColorRepository, ObjectId> {
}
