package com.ecommerce.arolaz.Order.Model;


import com.ecommerce.arolaz.OrderDetails.Model.OrderDetails;
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

    String unregisteredUserId;

    private List<OrderDetails> orderDetails;

    public Order(){}

    public Order(String securityUserId, Double totalPrice, int totalQuantity, String note, String address, String unregisteredUserId, List<OrderDetails> orderDetails) {
        this.securityUserId = securityUserId;
        this.totalPrice = totalPrice;
        this.totalQuantity = totalQuantity;
        this.note = note;
        this.address = address;
        this.unregisteredUserId = unregisteredUserId;
        this.orderDetails = orderDetails;
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

    public String getUnregisteredUserId() {
        return unregisteredUserId;
    }

    public void setUnregisteredUserId(String unregisteredUserId) {
        this.unregisteredUserId = unregisteredUserId;
    }

    public List<OrderDetails> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetails> orderDetails) {
        this.orderDetails = orderDetails;
    }
}

