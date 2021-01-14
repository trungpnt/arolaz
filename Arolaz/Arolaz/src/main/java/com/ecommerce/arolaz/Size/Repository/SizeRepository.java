package com.ecommerce.arolaz.Size.Repository;

import com.ecommerce.arolaz.Size.Model.Size;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SizeRepository extends MongoRepository<Size, ObjectId> {
    Optional<Size> findBySizeName (String sizeName);
    Optional<Size> findBySizeId(ObjectId sizeId);
    boolean existsBySizeName(String sizeName);
}
