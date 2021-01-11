package com.ecommerce.arolaz.Product.RestfulParams.domain;

public enum Filter {
  PAGE_SIZE("pageSize"),
  PAGE("page"),
  SELECT("select");
  private String code;

  Filter(String code) {
    this.code = code;
  }

  public String getCode() {
    return code;
  }

}
