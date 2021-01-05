package com.ecommerce.arolaz.Arolaz.Order.RequestResponseModels;

import com.ecommerce.arolaz.Arolaz.OrderDetails.RequestResponseModels.CreateOrderDetailsElementModel;

import java.util.ArrayList;

/**
 * CreateOrderRequestModel
 */
public class CreateOrderRequestModel {
  private String address,note;

  private ArrayList<CreateOrderDetailsElementModel> createOrderDetails;

  public CreateOrderRequestModel(){

  }

  public CreateOrderRequestModel(String address, String note, ArrayList<CreateOrderDetailsElementModel> createOrderDetails) {
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

  public ArrayList<CreateOrderDetailsElementModel> getCreateOrderDetails() {
    return createOrderDetails;
  }

  public void setCreateOrderDetails(ArrayList<CreateOrderDetailsElementModel> createOrderDetails) {
    this.createOrderDetails = createOrderDetails;
  }
}
