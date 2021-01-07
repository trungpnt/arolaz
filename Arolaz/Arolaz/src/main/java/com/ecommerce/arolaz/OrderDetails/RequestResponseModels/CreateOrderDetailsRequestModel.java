package com.ecommerce.arolaz.OrderDetails.RequestResponseModels;

public class CreateOrderDetailsRequestModel {

    private String productId;
    private String sizeId;
    private String colorId;
    private int quantity;

    public CreateOrderDetailsRequestModel(String productId, String sizeId, String colorId, int quantity) {
        this.productId = productId;
        this.sizeId = sizeId;
        this.colorId = colorId;
        this.quantity = quantity;
    }

    public CreateOrderDetailsRequestModel(){}

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getSizeId() {
        return sizeId;
    }

    public void setSizeId(String sizeId) {
        this.sizeId = sizeId;
    }

    public String getColorId() {
        return colorId;
    }

    public void setColorId(String colorId) {
        this.colorId = colorId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
