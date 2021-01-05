package com.ecommerce.arolaz.Arolaz.Category.Service;

import com.ecommerce.arolaz.Arolaz.Category.Model.Category;

import java.util.List;

public interface CategoryService {
    Category findByCategoryName(String categoryName);
    Category addCategory(Category category);
    List<Category> getAllCategories();
}
