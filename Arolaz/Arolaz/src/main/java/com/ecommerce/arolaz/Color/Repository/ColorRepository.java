package com.ecommerce.arolaz.Color.Repository;


import com.ecommerce.arolaz.Color.Model.Color;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ColorRepository extends MongoRepository<Color, ObjectId> {
    Optional<Color> findByColorName (String colorName);
    boolean existsByColorName (String colorName);
}
