package com.ecommerce.arolaz.Product.RequestResponseModels;

public class CreateOrderedProductRequestModel {

    private String productId;

    private String sizeId;

    private int amount;
    
    public CreateOrderedProductRequestModel(){}

    public CreateOrderedProductRequestModel(String productId, String sizeId, int amount) {
        this.productId = productId;
        this.sizeId = sizeId;
        this.amount = amount;
    }

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

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
