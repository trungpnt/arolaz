package com.ecommerce.arolaz.Category.Controller;


import com.ecommerce.arolaz.Category.Model.Category;
import com.ecommerce.arolaz.Category.Repository.CategoryRepository;
import com.ecommerce.arolaz.Category.RequestResponseModels.CategoryResponseModel;
import com.ecommerce.arolaz.Category.RequestResponseModels.CreateCategoryRequestModel;
import com.ecommerce.arolaz.Category.Service.CategoryService;
import com.ecommerce.arolaz.utils.ObjectCreationSuccessResponse;
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
import java.util.Optional;
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
        Optional<Category> found = categoryService.findByCategoryName(category.getCategoryName().toUpperCase());
        if(found.isPresent() ){
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
        Optional<Category> category = categoryService.findByCategoryName(categoryName);
        if(category == null){
            throw new EntityNotFoundException(categoryName);
        }
        categoryResponseModel.setId(category.get().getCategoryId().toString());
        categoryResponseModel.setName(category.get().getCategoryName());
        categoryResponseModel.setImgUrl(category.get().getImgUrl());
        return new ResponseEntity<>(categoryResponseModel,HttpStatus.OK);
    }

    /**
     * Find category by Id
     * */
    @GetMapping(path="/category/{categoryId}")
    public CategoryResponseModel findById(@PathVariable("categoryId") String categoryId){
        ObjectId categoryObjectId = new ObjectId(categoryId);

        Optional<Category> found = categoryRepository.findById(categoryObjectId);
        if(!found.isPresent()){
            throw new EntityNotFoundException("Category not found!");
        }
        return toDto(found.get());
    }


}
