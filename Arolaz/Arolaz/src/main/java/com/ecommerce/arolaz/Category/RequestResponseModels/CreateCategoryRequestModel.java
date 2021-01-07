package com.ecommerce.arolaz.Category.RequestResponseModels;


public class CreateCategoryRequestModel {
  private String categoryName;

  public CreateCategoryRequestModel(String categoryName) {
    this.categoryName = categoryName;
  }

  CreateCategoryRequestModel(){}

  private String imgUrl;

  public String getImgUrl() {
    return imgUrl;
  }

  public void setImgUrl(String imgUrl) {
    this.imgUrl = imgUrl;
  }

  public String getCategoryName() {
    return categoryName;
  }

  public void setCategoryName(String categoryName) {
    this.categoryName = categoryName.toUpperCase();
  }
}

