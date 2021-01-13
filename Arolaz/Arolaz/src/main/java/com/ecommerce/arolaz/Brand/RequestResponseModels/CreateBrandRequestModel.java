package com.ecommerce.arolaz.Brand.RequestResponseModels;


public class CreateBrandRequestModel {
  private String brandName;

  public CreateBrandRequestModel(){}
  
  public CreateBrandRequestModel(String brandName) {
    this.brandName = brandName;
  }

  public String getBrandName() {
    return brandName;
  }

  public void setBrandName(String brandName) {
    this.brandName = brandName;
  }
}

