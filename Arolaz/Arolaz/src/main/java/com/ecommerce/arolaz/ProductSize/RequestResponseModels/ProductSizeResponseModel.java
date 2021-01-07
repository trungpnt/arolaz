package com.ecommerce.arolaz.ProductSize.RequestResponseModels;

public class ProductSizeResponseModel {
    private String productSizeId;
    private String productId;
    private String sizeName;
    private Double price;

    public ProductSizeResponseModel(){}

    public ProductSizeResponseModel(String productSizeId, String productId, String sizeName, Double price) {
        this.productSizeId = productSizeId;
        this.productId = productId;
        this.sizeName = sizeName;
        this.price = price;
    }

    public String getProductSizeId() {
        return productSizeId;
    }

    public void setProductSizeId(String productSizeId) {
        this.productSizeId = productSizeId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
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
}
