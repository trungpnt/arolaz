package com.ecommerce.arolaz.ProductSize.Model;

import com.querydsl.core.annotations.QueryEntity;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Document(collection = "product_size")
@QueryEntity
public class ProductSize {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private ObjectId productSizeId;

    public ObjectId getProductSizeId() {
        return productSizeId;
    }

    private Double price;

    private String sizeName;

    private String productId;

    public ProductSize(){}

    public ProductSize(Double price, String sizeName, String productId) {
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
