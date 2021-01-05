package com.ecommerce.arolaz.Arolaz.Category.Repository;


import com.ecommerce.arolaz.Arolaz.Category.Model.Category;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CategoryRepository extends MongoRepository<Category, ObjectId>{
    Category findByCategoryName(String categoryName);
}
