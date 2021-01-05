package com.ecommerce.arolaz.SecurityUser.RequestResponseModels;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;

/**
 * EditUserPasswordRequestModel
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2020-09-27T18:50:33.498629500+07:00[Asia/Bangkok]")

public class EditUserPasswordRequestModel {
  @JsonProperty("password")
  private String password;

  @JsonProperty("newPassword")
  private String newPassword;

  protected EditUserPasswordRequestModel(){}

  public EditUserPasswordRequestModel password(String password) {
    this.password = password;
    return this;
  }

  /**
   * Get password
   * @return password
  */
  @ApiModelProperty(example = "abc", value = "")


  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public EditUserPasswordRequestModel newPassword(String newPassword) {
    this.newPassword = newPassword;
    return this;
  }

  /**
   * Get newPassword
   * @return newPassword
  */
  @ApiModelProperty(example = "afaw", value = "")


  public String getNewPassword() {
    return newPassword;
  }

  public void setNewPassword(String newPassword) {
    this.newPassword = newPassword;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EditUserPasswordRequestModel editUserPasswordRequestModel = (EditUserPasswordRequestModel) o;
    return Objects.equals(this.password, editUserPasswordRequestModel.password) &&
        Objects.equals(this.newPassword, editUserPasswordRequestModel.newPassword);
  }

  @Override
  public int hashCode() {
    return Objects.hash(password, newPassword);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EditUserPasswordRequestModel {\n");

    sb.append("    password: ").append(toIndentedString(password)).append("\n");
    sb.append("    newPassword: ").append(toIndentedString(newPassword)).append("\n");
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

