package com.ecommerce.arolaz.UnregisteredUsers.RequestResponseModels;

import com.ecommerce.arolaz.Order.RequestResponseModels.CreateOrderRequestModel;
import com.ecommerce.arolaz.OrderDetails.RequestResponseModels.CreateOrderDetailsRequestModel;

public class UnregisteredUserOrderRequestModel {
    private CreateUnregisteredUserModel unregisteredUser;
    private CreateOrderRequestModel createOrderDetails;

    public UnregisteredUserOrderRequestModel(){}

    public UnregisteredUserOrderRequestModel(CreateUnregisteredUserModel unregisteredUser, CreateOrderRequestModel createOrderDetails) {
        this.unregisteredUser = unregisteredUser;
        this.createOrderDetails = createOrderDetails;
    }

    public CreateUnregisteredUserModel getUnregisteredUser() {
        return unregisteredUser;
    }

    public void setUnregisteredUser(CreateUnregisteredUserModel unregisteredUser) {
        this.unregisteredUser = unregisteredUser;
    }

    public CreateOrderRequestModel getCreateOrderDetails() {
        return createOrderDetails;
    }

    public void setCreateOrderDetails(CreateOrderRequestModel createOrderDetails) {
        this.createOrderDetails = createOrderDetails;
    }
}
