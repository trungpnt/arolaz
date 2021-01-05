package com.ecommerce.arolaz.OrderDetails.RequestResponseModels;


import java.util.ArrayList;

public class CreateOrderDetailsElementModel {
    private String productId;
    private String productSizeId;
    private Double unitPrice;

    public CreateOrderDetailsElementModel(){}

    public CreateOrderDetailsElementModel(String productId, String productSizeId, Double unitPrice) {
        this.productId = productId;
        this.productSizeId = productSizeId;
        this.unitPrice = unitPrice;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductSizeId() {
        return productSizeId;
    }

    public void setProductSizeId(String productSizeId) {
        this.productSizeId = productSizeId;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }
}
