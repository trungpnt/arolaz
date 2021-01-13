package com.ecommerce.arolaz.Brand.RequestResponseModels;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * BrandListResponseModel
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2020-09-27T18:50:33.498629500+07:00[Asia/Bangkok]")

public class BrandListResponseModel   {
  @JsonProperty("categories")
  @Valid
  private List<BrandResponseModel> brands = null;

  public BrandListResponseModel(@Valid List<BrandResponseModel> brand) {
    this.brands = brand;
  }

  public BrandListResponseModel addCategoriesItem(BrandResponseModel brandItem) {
    if (this.brands == null) {
      this.brands = new ArrayList<>();
    }
    this.brands.add(brandItem);
    return this;
  }

  public List<BrandResponseModel> getBrands() {
    return brands;
  }

  public void setBrands(List<BrandResponseModel> brands) {
    this.brands = brands;
  }

  /**
   * Get brands
   * @return brands
  */
  @Override
  public int hashCode() {
    return super.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    return super.equals(obj);
  }

  @Override
  protected Object clone() throws CloneNotSupportedException {
    return super.clone();
  }

  @Override
  public String toString() {
    return super.toString();
  }
}

