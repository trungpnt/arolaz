package com.ecommerce.arolaz.Arolaz.Order.Model;


import com.ecommerce.arolaz.Arolaz.OrderDetails.Model.OrderDetails;
import com.querydsl.core.annotations.QueryEntity;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.util.List;

@QueryEntity
@Document(collection="orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private ObjectId orderId;

    private String securityUserId;

    private Double totalPrice;

    private int totalQuantity;

    private String note;

    private String address;

    private List<OrderDetails> products;

    public Order(){}

    public Order(String securityUserId, Double totalPrice, int totalQuantity, String note, String address, List<OrderDetails> products) {
        this.securityUserId = securityUserId;
        this.totalPrice = totalPrice;
        this.totalQuantity = totalQuantity;
        this.note = note;
        this.address = address;
        this.products = products;
    }

    public ObjectId getOrderId() {
        return orderId;
    }

    public String getSecurityUserId() {
        return securityUserId;
    }

    public void setSecurityUserId(String securityUserId) {
        this.securityUserId = securityUserId;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<OrderDetails> getProducts() {
        return products;
    }

    public void setProducts(List<OrderDetails> products) {
        this.products = products;
    }
}

