package com.ecommerce.arolaz.SecurityUser.RequestResponseModels;

public class UserLoginResponseModel {
  private String email;

  private String firstName;

  private String lastName;

  private String phone;

  private String address;

  protected UserLoginResponseModel(){}

  public UserLoginResponseModel(String email, String firstName, String lastName, String phone, String address) {
    this.email = email;
    this.firstName = firstName;
    this.lastName = lastName;
    this.phone = phone;
    this.address = address;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
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

