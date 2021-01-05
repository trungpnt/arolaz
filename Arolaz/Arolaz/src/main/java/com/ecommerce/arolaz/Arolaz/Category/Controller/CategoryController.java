package com.ecommerce.arolaz.Arolaz.Category.Controller;


import com.ecommerce.arolaz.Arolaz.Category.Model.Category;
import com.ecommerce.arolaz.Arolaz.Category.Repository.CategoryRepository;
import com.ecommerce.arolaz.Arolaz.Category.RequestResponseModels.CategoryResponseModel;
import com.ecommerce.arolaz.Arolaz.Category.RequestResponseModels.CreateCategoryRequestModel;
import com.ecommerce.arolaz.Arolaz.Category.Service.CategoryService;
import com.ecommerce.arolaz.Arolaz.utils.ObjectCreationSuccessResponse;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@CrossOrigin
@RequestMapping("/api")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CategoryRepository categoryRepository;

    @PostMapping(path="/category")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ObjectCreationSuccessResponse> createCategory(@Valid @RequestBody CreateCategoryRequestModel createCategoryRequestModel) {
        Category category = modelMapper.map(createCategoryRequestModel,Category.class);
        ObjectCreationSuccessResponse result = new ObjectCreationSuccessResponse();
        Category found = categoryService.findByCategoryName(category.getCategoryName().toUpperCase());
        if(found != null ){
            result.setId("CATEGORY WITH THE SAME NAME ALREADY EXISTS");
            result.setResponseCode(HttpStatus.FORBIDDEN.value());
            return new ResponseEntity<ObjectCreationSuccessResponse>(result,HttpStatus.FORBIDDEN);
        }
        Category persistCategory = categoryService.addCategory(category);
        result.setId(persistCategory.getCategoryId().toString());
        result.setResponseCode(HttpStatus.CREATED.value());
        return new ResponseEntity<ObjectCreationSuccessResponse>(result,HttpStatus.CREATED);
    }

    @GetMapping(path = "/category")
    @ResponseStatus(HttpStatus.OK)
    public List<CategoryResponseModel> getAllCategories() {
        List<CategoryResponseModel> categoryResponseModels;
        categoryResponseModels = categoryService.getAllCategories().stream().map(
                category -> toDto(category)
        ).collect(Collectors.toList());

        return categoryResponseModels;
    }
   /* @GetMapping(path = "/category")
    @ResponseStatus(HttpStatus.OK)
    public List<Category> getAllCategories() {

        return categoryService.getAllCategories();
    }*/

    public CategoryResponseModel toDto(Category category){
        return new CategoryResponseModel(category.getCategoryId().toString(),category.getCategoryName(),category.getImgUrl());
    }

    @GetMapping(path = "/category/{categoryName}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CategoryResponseModel> getCategoryByName(@PathVariable( value = "categoryName") String categoryName) {
        CategoryResponseModel categoryResponseModel = new CategoryResponseModel();
        categoryName = categoryName.substring(0,1).toUpperCase() + categoryName.substring(1);
        Category category = categoryService.findByCategoryName(categoryName);
        if(category == null){
            throw new EntityNotFoundException(categoryName);
        }
        categoryResponseModel.setId(category.getCategoryId().toString());
        categoryResponseModel.setName(category.getCategoryName());
        categoryResponseModel.setImgUrl(category.getImgUrl());
        return new ResponseEntity<>(categoryResponseModel,HttpStatus.OK);
    }

    //Find CategoryById
    @GetMapping(path="/category/find")
    public CategoryResponseModel findById(@RequestParam("categoryId") ObjectId categoryId){
        return toDto(categoryRepository.findById(categoryId).get());
    }
//    private ResponseEntity<CategoryListResponseModel> buildCategoryListResponse(List<Category> categoryList){
//        CategoryListResponseModel categoryListResponse = new CategoryListResponseModel();
//        if(categoryList != null){
//            categoryList.forEach(item -> categoryListResponse.addCategoriesItem(modelMapper.map(item, CategoryResponseModel.class)));
//        }
//        return new ResponseEntity<>(categoryListResponse,HttpStatus.OK);
//    }

}
