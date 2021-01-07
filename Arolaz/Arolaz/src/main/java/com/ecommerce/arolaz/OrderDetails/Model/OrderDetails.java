package com.ecommerce.arolaz.OrderDetails.Model;

import com.querydsl.core.annotations.QueryEntity;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@QueryEntity
@Document(collection = "order_details")
public class OrderDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private ObjectId orderDetailsId;

    private String orderId;

    private String productId;

    private Double unitPrice;

    private int quantity;

    private String productSizeId;

    private Double calculatedPrice;

    private String colorId;

    public OrderDetails(){}

    public OrderDetails(String orderId, String productId, Double unitPrice, int quantity, String productSizeId, Double calculatedPrice, String colorId) {
        this.orderId = orderId;
        this.productId = productId;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.productSizeId = productSizeId;
        this.calculatedPrice = calculatedPrice;
        this.colorId = colorId;
    }

    public ObjectId getOrderDetailsId() {
        return orderDetailsId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getProductSizeId() {
        return productSizeId;
    }

    public void setProductSizeId(String productSizeId) {
        this.productSizeId = productSizeId;
    }

    public Double getCalculatedPrice() {
        return calculatedPrice;
    }

    public void setCalculatedPrice(Double calculatedPrice) {
        this.calculatedPrice = calculatedPrice;
    }

    public String getColorId() {
        return colorId;
    }

    public void setColorId(String colorId) {
        this.colorId = colorId;
    }
}
