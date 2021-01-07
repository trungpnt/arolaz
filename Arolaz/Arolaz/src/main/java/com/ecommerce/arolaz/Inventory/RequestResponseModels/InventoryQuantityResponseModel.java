package com.ecommerce.arolaz.Inventory.RequestResponseModels;

public class InventoryQuantityResponseModel {
    private String inventoryId;
    private int quantity;
    public InventoryQuantityResponseModel(){

    }

    public InventoryQuantityResponseModel(String inventoryId, int quantity) {
        this.inventoryId = inventoryId;
        this.quantity = quantity;
    }

    public String getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(String inventoryId) {
        this.inventoryId = inventoryId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
