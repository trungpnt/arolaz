package com.ecommerce.arolaz.Category.RequestResponseModels;

public class CategoryResponseModel   {
  private String id;

  private String name;

  private String imgUrl;

  public CategoryResponseModel(){}

  public CategoryResponseModel(String id, String name, String imgUrl) {
    this.id = id;
    this.name = name;
    this.imgUrl = imgUrl;
  }

  public String getImgUrl() {
    return imgUrl;
  }

  public void setImgUrl(String imgUrl) {
    this.imgUrl = imgUrl;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}

