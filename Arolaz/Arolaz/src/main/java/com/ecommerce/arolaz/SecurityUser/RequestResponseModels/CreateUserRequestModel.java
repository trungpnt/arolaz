package com.ecommerce.arolaz.SecurityUser.RequestResponseModels;

/**
 * CreateUserRequestModel
 */

public class CreateUserRequestModel {

  private String email;

  private String fullName;

  private String phone;

  private String password;

  private String address;

  protected CreateUserRequestModel(){}

  public CreateUserRequestModel(String email, String fullName, String phone, String password, String address) {
    this.email = email;
    this.fullName = fullName;
    this.phone = phone;
    this.password = password;
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

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }
}

