package com.ecommerce.arolaz.SecurityUser.RequestResponseModels;

public class UserLoginResponseModel {
  private String email;

  private String fullName;

  private String phone;

  private String address;

  protected UserLoginResponseModel(){}

  public UserLoginResponseModel(String email, String fullName, String phone, String address) {
    this.email = email;
    this.fullName = fullName;
    this.phone = phone;
    this.address = address;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }
}

