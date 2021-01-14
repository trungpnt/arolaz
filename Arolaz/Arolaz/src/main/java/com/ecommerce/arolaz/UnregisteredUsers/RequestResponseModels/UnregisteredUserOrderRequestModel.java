package com.ecommerce.arolaz.UnregisteredUsers.RequestResponseModels;

import com.ecommerce.arolaz.Order.RequestResponseModels.CreateOrderRequestModel;
import com.ecommerce.arolaz.OrderDetails.RequestResponseModels.CreateOrderDetailsRequestModel;

public class UnregisteredUserOrderRequestModel {
    private CreateUnregisteredUserModel unregisteredUser;
    private CreateOrderRequestModel createOrderRequestModel;

    public UnregisteredUserOrderRequestModel(){}

    public UnregisteredUserOrderRequestModel(CreateUnregisteredUserModel unregisteredUser, CreateOrderRequestModel createOrderRequestModel) {
        this.unregisteredUser = unregisteredUser;
        this.createOrderRequestModel = createOrderRequestModel;
    }

    public CreateUnregisteredUserModel getUnregisteredUser() {
        return unregisteredUser;
    }

    public void setUnregisteredUser(CreateUnregisteredUserModel unregisteredUser) {
        this.unregisteredUser = unregisteredUser;
    }

    public CreateOrderRequestModel getCreateOrderRequestModel() {
        return createOrderRequestModel;
    }

    public void setCreateOrderRequestModel(CreateOrderRequestModel createOrderRequestModel) {
        this.createOrderRequestModel = createOrderRequestModel;
    }
}
