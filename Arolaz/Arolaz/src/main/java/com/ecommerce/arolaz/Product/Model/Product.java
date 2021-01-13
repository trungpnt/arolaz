package com.ecommerce.arolaz.Product.Model;

import com.ecommerce.arolaz.Product.RequestResponseModels.ProductSizePriceQuantityColor;
import com.querydsl.core.annotations.QueryEntity;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.querydsl.binding.QuerydslPredicate;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.util.List;

@QueryEntity
@QuerydslPredicate
@Document(collection = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private ObjectId productId;

    private String categoryId;

    private String brandId;

    private String imgUrl;

    private String description;

    private String productName;

    private String categoryName;

    private String brandName;

    private Double basicSmallSizePrice;

    private String productDescription;

    private String basicColorName;

    private String basicSizeName;

    private List<ProductSizePriceQuantityColor> productSizePriceQuantityColors;

    public Product(){}


    public Product(String categoryId, String brandId, String imgUrl, String description, String productName, String categoryName, String brandName, Double basicSmallSizePrice, String productDescription, String basicColorName, String basicSizeName, List<ProductSizePriceQuantityColor> productSizePriceQuantityColors) {
        this.categoryId = categoryId;
        this.brandId = brandId;
        this.imgUrl = imgUrl;
        this.description = description;
        this.productName = productName;
        this.categoryName = categoryName;
        this.brandName = brandName;
        this.basicSmallSizePrice = basicSmallSizePrice;
        this.productDescription = productDescription;
        this.basicColorName = basicColorName;
        this.basicSizeName = basicSizeName;
        this.productSizePriceQuantityColors = productSizePriceQuantityColors;
    }

    //For unit testing
    public Product(ObjectId productId, String name, Double basicSmallSizePrice, String imgUrl, String description) {
        this.productId = productId;
        this.imgUrl = imgUrl;
        this.productName = productName;
        this.basicSmallSizePrice = basicSmallSizePrice;
        this.productDescription = productDescription;
    }

    public Product(ObjectId productId, String categoryId, String name, Double basicSmallSizePrice, String imgUrl, String description) {
        this.productId = productId;
        this.imgUrl = imgUrl;
        this.productName = name;
        this.basicSmallSizePrice = basicSmallSizePrice;
        this.productDescription = description;
        this.categoryId = categoryId;
    }

    public ObjectId getProductId() {
        return productId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

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

    public Double getBasicSmallSizePrice() {
        return basicSmallSizePrice;
    }

    public void setBasicSmallSizePrice(Double basicSmallSizePrice) {
        this.basicSmallSizePrice = basicSmallSizePrice;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getBasicColorName() {
        return basicColorName;
    }

    public void setBasicColorName(String basicColorName) {
        this.basicColorName = basicColorName;
    }

    public String getBasicSizeName() {
        return basicSizeName;
    }

    public void setBasicSizeName(String basicSizeName) {
        this.basicSizeName = basicSizeName;
    }

    public List<ProductSizePriceQuantityColor> getProductSizePriceQuantityColors() {
        return productSizePriceQuantityColors;
    }

    public void setProductSizePriceQuantityColors(List<ProductSizePriceQuantityColor> productSizePriceQuantityColors) {
        this.productSizePriceQuantityColors = productSizePriceQuantityColors;
    }
}
