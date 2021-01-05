package com.ecommerce.arolaz.Product.RequestResponseModels;

public class CreateProductRequestModel {

  private String categoryId;

  private String brandId;

  private String colorId;

  private String productSizeId;

  private Double price;

  private String name;

  private String description;

  public CreateProductRequestModel(){}

  public CreateProductRequestModel(String categoryId, String brandId, String colorId, String productSizeId, Double price, String name, String description) {
    this.categoryId = categoryId;
    this.brandId = brandId;
    this.colorId = colorId;
    this.productSizeId = productSizeId;
    this.price = price;
    this.name = name;
    this.description = description;
  }

  public String getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(String categoryId) {
    this.categoryId = categoryId;
  }

  public String getBrandId() {
    return brandId;
  }

  public void setBrandId(String brandId) {
    this.brandId = brandId;
  }

  public String getColorId() {
    return colorId;
  }

  public void setColorId(String colorId) {
    this.colorId = colorId;
  }

  public String getProductSizeId() {
    return productSizeId;
  }

  public void setProductSizeId(String productSizeId) {
    this.productSizeId = productSizeId;
  }

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}

