package com.ecommerce.arolaz.Order.RequestResponseModels;

public class PagedOrderResponseModel {
    private String id;
    private Double totalPrice;
    private int amount;
    private String address;
    private String note;

    PagedOrderResponseModel(){}

    public PagedOrderResponseModel(String id, Double totalPrice, int amount, String address, String note) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.amount = amount;
        this.address = address;
        this.note = note;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
