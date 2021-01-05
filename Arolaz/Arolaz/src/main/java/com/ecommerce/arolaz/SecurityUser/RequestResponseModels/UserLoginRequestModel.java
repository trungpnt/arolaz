package com.ecommerce.arolaz.SecurityUser.RequestResponseModels;

import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;

/**
 * UserLoginRequestModel
 */
public class UserLoginRequestModel {
  private String requiredEntry;

  private String password;

  public UserLoginRequestModel requiredEntry(String requiredEntry) {
    this.requiredEntry = requiredEntry;
    return this;
  }
  protected UserLoginRequestModel(){}
  /**
   * Get requiredEntry
   * @return requiredEntry
  */
  public String getRequiredEntry() {
    return requiredEntry;
  }

  public void setRequiredEntry(String requiredEntry) {
    this.requiredEntry = requiredEntry;
  }

  public UserLoginRequestModel password(String password) {
    this.password = password;
    return this;
  }

  /**
   * Get password
   * @return password
  */
  @ApiModelProperty(value = "")


  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserLoginRequestModel userLoginRequestModel = (UserLoginRequestModel) o;
    return Objects.equals(this.requiredEntry, userLoginRequestModel.requiredEntry) &&
        Objects.equals(this.password, userLoginRequestModel.password);
  }

  @Override
  public int hashCode() {
    return Objects.hash(requiredEntry, password);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UserLoginRequestModel {\n");

    sb.append("    requiredEntry: ").append(toIndentedString(requiredEntry)).append("\n");
    sb.append("    password: ").append(toIndentedString(password)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

