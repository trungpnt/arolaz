package com.ecommerce.arolaz.Arolaz.OrderDetails.RequestResponseModels;

import java.util.ArrayList;

public class CreateOrderDetailsRequestModel {
  private ArrayList<CreateOrderDetailsElementModel> createOrderDetailsElementModels = new ArrayList<>();
  public CreateOrderDetailsRequestModel(){}
  public CreateOrderDetailsRequestModel(ArrayList<CreateOrderDetailsElementModel> createOrderDetailsElementModels) {
    this.createOrderDetailsElementModels = createOrderDetailsElementModels;
  }

  public ArrayList<CreateOrderDetailsElementModel> getCreateOrderDetailsElementModels() {
    return createOrderDetailsElementModels;
  }

  public void setCreateOrderDetailsElementModels(ArrayList<CreateOrderDetailsElementModel> createOrderDetailsElementModels) {
    this.createOrderDetailsElementModels = createOrderDetailsElementModels;
  }
}

