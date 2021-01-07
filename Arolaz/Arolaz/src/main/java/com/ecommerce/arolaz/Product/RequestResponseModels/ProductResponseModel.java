package com.ecommerce.arolaz.Product.RequestResponseModels;

import com.ecommerce.arolaz.Color.Model.Color;
import com.ecommerce.arolaz.ProductSize.Model.ProductSize;

import java.util.List;

/**
 * ProductResponseModel
 */

public class ProductResponseModel   {
  private String productId;

  private String name;

  private String categoryName;

  private String imgUrl;

  private String brandName;

  private String description;

  private List<ProductSizePriceQuantityColor> productSizePriceQuantityColors;

  public ProductResponseModel(){}

  public ProductResponseModel(String productId, String name, String categoryName, String imgUrl, String brandName, String description, List<ProductSizePriceQuantityColor> productSizePriceQuantityColors) {
    this.productId = productId;
    this.name = name;
    this.categoryName = categoryName;
    this.imgUrl = imgUrl;
    this.brandName = brandName;
    this.description = description;
    this.productSizePriceQuantityColors = productSizePriceQuantityColors;
  }

  public ProductResponseModel(String name, String categoryName, String imgUrl, String brandName, String description, List<ProductSizePriceQuantityColor> productSizePriceQuantityColors) {
    this.name = name;
    this.categoryName = categoryName;
    this.imgUrl = imgUrl;
    this.brandName = brandName;
    this.description = description;
    this.productSizePriceQuantityColors = productSizePriceQuantityColors;
  }

  public String getProductId() {
    return productId;
  }

  public void setProductId(String productId) {
    this.productId = productId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCategoryName() {
    return categoryName;
  }

  public void setCategoryName(String categoryName) {
    this.categoryName = categoryName;
  }

  public String getImgUrl() {
    return imgUrl;
  }

  public void setImgUrl(String imgUrl) {
    this.imgUrl = imgUrl;
  }

  public String getBrandName() {
    return brandName;
  }

  public void setBrandName(String brandName) {
    this.brandName = brandName;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public List<ProductSizePriceQuantityColor> getProductSizePriceQuantityColors() {
    return productSizePriceQuantityColors;
  }

  public void setProductSizePriceQuantityColors(List<ProductSizePriceQuantityColor> productSizePriceQuantityColors) {
    this.productSizePriceQuantityColors = productSizePriceQuantityColors;
  }
}

