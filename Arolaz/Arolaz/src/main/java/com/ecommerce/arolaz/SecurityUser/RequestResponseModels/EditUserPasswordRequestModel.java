package com.ecommerce.arolaz.SecurityUser.RequestResponseModels;

public class EditUserPasswordRequestModel {

  private String currentPassword;

  private String newPassword;

  protected EditUserPasswordRequestModel(){}

  public EditUserPasswordRequestModel(String currentPassword, String newPassword) {
    this.currentPassword = currentPassword;
    this.newPassword = newPassword;
  }

  public String getCurrentPassword() {
    return currentPassword;
  }

  public void setCurrentPassword(String currentPassword) {
    this.currentPassword = currentPassword;
  }

  public String getNewPassword() {
    return newPassword;
  }

  public void setNewPassword(String newPassword) {
    this.newPassword = newPassword;
  }
}

