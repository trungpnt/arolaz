package com.ecommerce.arolaz.ProductSize.RequestResponseModels;

public class CreateProductSizeRequestModel {
    private Double price;
    private String sizeName;
    private String productId;

    public CreateProductSizeRequestModel(){}

    public CreateProductSizeRequestModel(Double price, String sizeName, String productId) {
        this.price = price;
        this.sizeName = sizeName;
        this.productId = productId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getSizeName() {
        return sizeName;
    }

    public void setSizeName(String sizeName) {
        this.sizeName = sizeName;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}
