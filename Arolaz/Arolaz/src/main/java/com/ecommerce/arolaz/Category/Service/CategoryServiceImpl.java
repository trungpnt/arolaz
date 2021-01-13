package com.ecommerce.arolaz.Category.Service;

import com.ecommerce.arolaz.Category.Model.Category;
import com.ecommerce.arolaz.Category.Repository.CategoryRepository;
import com.ecommerce.arolaz.Utils.ExceptionHandlers.CategoryNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Optional<Category> findByCategoryName(String name) {
        Optional<Category> categoryFound = categoryRepository.findByCategoryName(name);
        if(!categoryFound.isPresent()){
            throw new CategoryNotFoundException(String.format("Category with '%s' not found!",name));
        }
        return categoryFound;
    }

    @Override
    public boolean existsByName(String name) {
        return categoryRepository.existsByCategoryName(name);
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
