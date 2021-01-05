package com.ecommerce.arolaz.OrderDetails.RequestResponseModels;

public class OrderDetailsResponseModel {
  private String id;

  private Integer amount;

  private String sizeId;

  public OrderDetailsResponseModel(String id, Integer amount, String sizeId) {
    this.id = id;
    this.amount = amount;
    this.sizeId = sizeId;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Integer getAmount() {
    return amount;
  }

  public void setAmount(Integer amount) {
    this.amount = amount;
  }

  public String getSizeId() {
    return sizeId;
  }

  public void setSizeId(String sizeId) {
    this.sizeId = sizeId;
  }
}

