package com.ecommerce.arolaz.UnregisteredUsers.RequestResponseModels;

public class CreateUnregisteredUserModel {
    private String unregisteredUserId;
    private String fullName;
    private String phoneNumber;
    private String address;
    private String email;

    public CreateUnregisteredUserModel(){}

    public CreateUnregisteredUserModel(String unregisteredUserId, String fullName, String phoneNumber, String address, String email) {
        this.unregisteredUserId = unregisteredUserId;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.email = email;
    }

    public String getUnregisteredUserId() {
        return unregisteredUserId;
    }

    public void setUnregisteredUserId(String unregisteredUserId) {
        this.unregisteredUserId = unregisteredUserId;
    }

    public CreateUnregisteredUserModel(String fullName, String phoneNumber, String address, String email) {
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
