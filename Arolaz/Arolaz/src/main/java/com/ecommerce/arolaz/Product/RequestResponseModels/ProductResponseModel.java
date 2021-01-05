package com.ecommerce.arolaz.Product.RequestResponseModels;

/**
 * ProductResponseModel
 */

public class ProductResponseModel   {
  private String id;

  private String name;

  private String categoryId;

  private String imgUrl;

  private String brandId;

  private String description;

  public ProductResponseModel(){}

  public ProductResponseModel(String id, String name, String categoryId, String imgUrl, String brandId, String description) {
    this.id = id;
    this.name = name;
    this.categoryId = categoryId;
    this.imgUrl = imgUrl;
    this.brandId = brandId;
    this.description = description;
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

  public String getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(String categoryId) {
    this.categoryId = categoryId;
  }

  public String getImgUrl() {
    return imgUrl;
  }

  public void setImgUrl(String imgUrl) {
    this.imgUrl = imgUrl;
  }

  public String getBrandId() {
    return brandId;
  }

  public void setBrandId(String brandId) {
    this.brandId = brandId;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}

