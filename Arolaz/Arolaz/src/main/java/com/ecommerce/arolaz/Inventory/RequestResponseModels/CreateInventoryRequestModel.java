package com.ecommerce.arolaz.Inventory.RequestResponseModels;

public class CreateInventoryRequestModel {
    private String productId;
    private String productSizeId;
    private String colorId;

    public CreateInventoryRequestModel(){}

    public CreateInventoryRequestModel(String productId, String productSizeId, String colorId) {
        this.productId = productId;
        this.productSizeId = productSizeId;
        this.colorId = colorId;
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

    public String getColorId() {
        return colorId;
    }

    public void setColorId(String colorId) {
        this.colorId = colorId;
    }
}
