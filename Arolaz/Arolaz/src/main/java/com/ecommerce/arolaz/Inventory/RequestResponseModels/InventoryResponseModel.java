package com.ecommerce.arolaz.Inventory.RequestResponseModels;

public class InventoryResponseModel {
    private String inventoryId;
    private String productId;
    private String productSizeId;
    private String colorId;
    private int quantity;

    public InventoryResponseModel(){}
    public InventoryResponseModel(String inventoryId, String productId, String productSizeId, String colorId, int quantity) {
        this.inventoryId = inventoryId;
        this.productId = productId;
        this.productSizeId = productSizeId;
        this.colorId = colorId;
        this.quantity = quantity;
    }

    public String getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(String inventoryId) {
        this.inventoryId = inventoryId;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
