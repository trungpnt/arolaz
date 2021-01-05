package com.ecommerce.arolaz.Arolaz.Category.Service;

import com.ecommerce.arolaz.Arolaz.Category.Model.Category;
import com.ecommerce.arolaz.Arolaz.Category.Repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category findByCategoryName(String name) {
        //name = name.substring(0,1).toUpperCase() + name.substring(1);
        return categoryRepository.findByCategoryName(name.toUpperCase());
    }

    @Override
    public Category addCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }



}
