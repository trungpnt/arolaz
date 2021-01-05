package com.ecommerce.arolaz.SecurityUser.RequestResponseModels;

import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;

/**
 * UserTokenResponseModel
 */
public class UserTokenResponseModel {
  private String token;

  public UserTokenResponseModel(String token) {
    this.token = token;
  }
  public UserTokenResponseModel(){}
  /**
   * Get token
   * @return token
  */
  @ApiModelProperty(value = "")

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserTokenResponseModel userTokenResponseModel = (UserTokenResponseModel) o;
    return Objects.equals(this.token, userTokenResponseModel.token);
  }

  @Override
  public int hashCode() {
    return Objects.hash(token);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UserTokenResponseModel {\n");

    sb.append("    token: ").append(toIndentedString(token)).append("\n");
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

