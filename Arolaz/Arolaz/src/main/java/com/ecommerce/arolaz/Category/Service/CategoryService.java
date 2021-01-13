package com.ecommerce.arolaz.Category.Service;

import com.ecommerce.arolaz.Category.Model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    Optional<Category> findByCategoryName(String categoryName);
    Category addCategory(Category category);
    List<Category> getAllCategories();
    boolean existsByName(String name);
}
