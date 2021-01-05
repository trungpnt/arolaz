package com.ecommerce.arolaz.Order.RequestResponseModels;

import com.ecommerce.arolaz.OrderDetails.Model.OrderDetails;

import java.util.List;

public class OrderResponseModel {

    private String orderId;

    private String securityUserId;

    private Double totalPrice;

    private int totalQuantity;

    private String note;

    private String address;

    private List<OrderDetails> products;
    public OrderResponseModel(){}
    public OrderResponseModel(String orderId, Double totalPrice, int totalQuantity, String address, String note, List<OrderDetails> products) {
        this.orderId = orderId;
        this.securityUserId = securityUserId;
        this.totalPrice = totalPrice;
        this.totalQuantity = totalQuantity;
        this.note = note;
        this.address = address;
        this.products = products;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
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
