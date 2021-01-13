package com.ecommerce.arolaz.Brand.RequestResponseModels;

public class BrandResponseModel   {
  private String id;

  private String brandName;

  public BrandResponseModel(){}
  public BrandResponseModel(String id, String brandName) {
    this.id = id;
    this.brandName = brandName;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getBrandName() {
    return brandName;
  }

  public void setBrandName(String brandName) {
    this.brandName = brandName;
  }
}

