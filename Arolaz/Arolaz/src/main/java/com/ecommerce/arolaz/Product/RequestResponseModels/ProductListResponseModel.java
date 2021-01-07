package com.ecommerce.arolaz.Product.RequestResponseModels;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * ProductListResponse
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2020-09-27T18:50:33.498629500+07:00[Asia/Bangkok]")

public class ProductListResponseModel {
  private List<ProductResponseModel> products = null;

  public ProductListResponseModel products(List<ProductResponseModel> products) {
    this.products = products;
    return this;
  }

  public ProductListResponseModel addProductsItem(ProductResponseModel productsItem) {
    if (this.products == null) {
      this.products = new ArrayList<>();
    }
    this.products.add(productsItem);
    return this;
  }

  /**
   * Get products
   * @return products
  */

  @Valid
  public List<ProductResponseModel> getProducts() {
    return products;
  }

  public void setProducts(List<ProductResponseModel> products) {
    this.products = products;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ProductListResponseModel productListResponseModel = (ProductListResponseModel) o;
    return Objects.equals(this.products, productListResponseModel.products);
  }

  @Override
  public int hashCode() {
    return Objects.hash(products);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ProductListResponse {\n");

    sb.append("    products: ").append(toIndentedString(products)).append("\n");
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

