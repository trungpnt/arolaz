package com.ecommerce.arolaz.Product.RequestResponseModels;

import com.ecommerce.arolaz.Color.Model.Color;
import com.ecommerce.arolaz.ProductSize.Model.ProductSize;

import java.util.List;

public class CreateProductRequestModel {

  private String categoryName;

  private String brandName;

  private List<String> productSizeNames;

  private List<Double> productSizePrices;

  private List<Integer> quantities;

  private String name;

  private List<String> availableProductColorNames;

  private String description;

  public CreateProductRequestModel(){}

  public CreateProductRequestModel(String categoryName, String brandName, List<String> productSizeNames, List<Double> productSizePrices, List<Integer> quantities, String name, List<String> availableProductColorNames, String description) {
    this.categoryName = categoryName;
    this.brandName = brandName;
    this.productSizeNames = productSizeNames;
    this.productSizePrices = productSizePrices;
    this.quantities = quantities;
    this.name = name;
    this.availableProductColorNames = availableProductColorNames;
    this.description = description;
  }

  public String getCategoryName() {
    return categoryName;
  }

  public void setCategoryName(String categoryName) {
    this.categoryName = categoryName;
  }

  public String getBrandName() {
    return brandName;
  }

  public void setBrandName(String brandName) {
    this.brandName = brandName;
  }

  public List<String> getProductSizeNames() {
    return productSizeNames;
  }

  public void setProductSizeNames(List<String> productSizeNames) {
    this.productSizeNames = productSizeNames;
  }

  public List<Double> getProductSizePrices() {
    return productSizePrices;
  }

  public void setProductSizePrices(List<Double> productSizePrices) {
    this.productSizePrices = productSizePrices;
  }

  public List<Integer> getQuantities() {
    return quantities;
  }

  public void setQuantities(List<Integer> quantities) {
    this.quantities = quantities;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<String> getAvailableProductColorNames() {
    return availableProductColorNames;
  }

  public void setAvailableProductColorNames(List<String> availableProductColorNames) {
    this.availableProductColorNames = availableProductColorNames;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}

