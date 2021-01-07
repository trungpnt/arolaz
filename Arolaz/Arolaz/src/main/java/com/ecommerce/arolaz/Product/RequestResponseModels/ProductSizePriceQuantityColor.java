package com.ecommerce.arolaz.Product.RequestResponseModels;

public class ProductSizePriceQuantityColor {
    private String sizeName;
    private Double price;
    private String colorName;
    private int quantity;

    public ProductSizePriceQuantityColor(String sizeName, Double price, String colorName, int quantity) {
        this.sizeName = sizeName;
        this.price = price;
        this.colorName = colorName;
        this.quantity = quantity;
    }

    public String getSizeName() {
        return sizeName;
    }

    public void setSizeName(String sizeName) {
        this.sizeName = sizeName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
