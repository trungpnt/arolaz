package com.ecommerce.arolaz.SecurityUser.RequestResponseModels;

/**
 * CreateUserRequestModel
 */

public class CreateUserRequestModel {

  private String email;

  private String firstName;

  private String lastName;

  private String phone;

  private String password;

  private String address;

  protected CreateUserRequestModel(){}

  public CreateUserRequestModel(String email, String firstName, String lastName, String phone, String password, String address) {
    this.email = email;
    this.firstName = firstName;
    this.lastName = lastName;
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

