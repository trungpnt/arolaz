package com.ecommerce.arolaz.Category.Service;

import com.ecommerce.arolaz.Category.Model.Category;

import java.util.List;

public interface CategoryService {
    Category findByCategoryName(String categoryName);
    Category addCategory(Category category);
    List<Category> getAllCategories();
}
