package com.ecommerce.arolaz.Product.Model;

public class ProductSearchCriteria {
    private String brandName;
    private String colorName;
    private Double price;
    private String productName;
    private String categoryName;
    private String sizeName;

    public ProductSearchCriteria(String brandName, String colorName, Double price, String productName, String categoryName, String sizeName) {
        this.brandName = brandName;
        this.colorName = colorName;
        this.price = price;
        this.productName = productName;
        this.categoryName = categoryName;
        this.sizeName = sizeName;
    }

    public String getSizeName() {
        return sizeName;
    }

    public void setSizeName(String sizeName) {
        this.sizeName = sizeName;
    }

    public ProductSearchCriteria(){}

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
