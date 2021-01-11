package com.ecommerce.arolaz.Product.RequestResponseModels;

import java.util.List;

/**
 * ProductResponseModel
 */

public class ProductResponseModel   {
  private String productId;

  private String productName;

  private String categoryName;

  private String imgUrl;

  private String brandName;

  private Double basicSmallSizePrice;

  private String description;

  private String basicColorName;

  private String basicSizeName;

  private List<ProductSizePriceQuantityColor> productSizePriceQuantityColors;

  public ProductResponseModel(){}

  public ProductResponseModel(String productId, String productName, String categoryName, String imgUrl, String brandName, Double basicSmallSizePrice, String description, String basicColorName, String basicSizeName, List<ProductSizePriceQuantityColor> productSizePriceQuantityColors) {
    this.productId = productId;
    this.productName = productName;
    this.categoryName = categoryName;
    this.imgUrl = imgUrl;
    this.brandName = brandName;
    this.basicSmallSizePrice = basicSmallSizePrice;
    this.description = description;
    this.basicColorName = basicColorName;
    this.basicSizeName = basicSizeName;
    this.productSizePriceQuantityColors = productSizePriceQuantityColors;
  }

  public String getBasicColorName() {
    return basicColorName;
  }

  public void setBasicColorName(String basicColorName) {
    this.basicColorName = basicColorName;
  }

  public String getBasicSizeName() {
    return basicSizeName;
  }

  public void setBasicSizeName(String basicSizeName) {
    this.basicSizeName = basicSizeName;
  }

  public String getProductId() {
    return productId;
  }

  public void setProductId(String productId) {
    this.productId = productId;
  }

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
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

  public Double getBasicSmallSizePrice() {
    return basicSmallSizePrice;
  }

  public void setBasicSmallSizePrice(Double basicSmallSizePrice) {
    this.basicSmallSizePrice = basicSmallSizePrice;
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

