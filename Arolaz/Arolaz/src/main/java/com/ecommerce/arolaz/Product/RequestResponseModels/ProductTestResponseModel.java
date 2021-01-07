package com.ecommerce.arolaz.Product.RequestResponseModels;

import java.util.List;

public class ProductTestResponseModel {
    private String productId;
    private String name;
    private String categoryId;
    private String imgUrl;
    private String brandId;
    private String description;
    private List<String> colors;

    public ProductTestResponseModel(String productId, String name, String categoryId, String imgUrl, String brandId, String description, List<String> colors) {
        this.productId = productId;
        this.name = name;
        this.categoryId = categoryId;
        this.imgUrl = imgUrl;
        this.brandId = brandId;
        this.description = description;
        this.colors = colors;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getColors() {
        return colors;
    }

    public void setColors(List<String> colors) {
        this.colors = colors;
    }
}
