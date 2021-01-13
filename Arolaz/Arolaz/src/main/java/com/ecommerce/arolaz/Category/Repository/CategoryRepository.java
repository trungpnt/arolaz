package com.ecommerce.arolaz.Category.Repository;


import com.ecommerce.arolaz.Category.Model.Category;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CategoryRepository extends MongoRepository<Category, ObjectId>{
    Optional<Category> findByCategoryName(String categoryName);
    boolean existsByCategoryName(String name);

}
