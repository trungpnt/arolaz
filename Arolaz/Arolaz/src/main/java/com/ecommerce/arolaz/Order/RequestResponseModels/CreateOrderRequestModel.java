package com.ecommerce.arolaz.Order.RequestResponseModels;

import com.ecommerce.arolaz.OrderDetails.RequestResponseModels.CreateOrderDetailsRequestModel;
import java.util.List;

/**
 * CreateOrderRequestModel
 */
public class CreateOrderRequestModel {

  private String address,note;

  private List<CreateOrderDetailsRequestModel> createOrderDetails;

  public CreateOrderRequestModel(){

  }

  public CreateOrderRequestModel(String address, String note, List<CreateOrderDetailsRequestModel> createOrderDetails) {
    this.address = address;
    this.note = note;
    this.createOrderDetails = createOrderDetails;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getNote() {
    return note;
  }

  public void setNote(String note) {
    this.note = note;
  }

  public List<CreateOrderDetailsRequestModel> getCreateOrderDetails() {
    return createOrderDetails;
  }

  public void setCreateOrderDetails(List<CreateOrderDetailsRequestModel> createOrderDetails) {
    this.createOrderDetails = createOrderDetails;
  }
}
