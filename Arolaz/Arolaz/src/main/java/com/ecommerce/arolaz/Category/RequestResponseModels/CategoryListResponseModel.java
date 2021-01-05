package com.ecommerce.arolaz.Category.RequestResponseModels;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * CategoryListResponseModel
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2020-09-27T18:50:33.498629500+07:00[Asia/Bangkok]")

public class CategoryListResponseModel   {
  @JsonProperty("categories")
  @Valid
  private List<CategoryResponseModel> categories = null;

  public CategoryListResponseModel categories(List<CategoryResponseModel> categories) {
    this.categories = categories;
    return this;
  }

  public CategoryListResponseModel addCategoriesItem(CategoryResponseModel categoriesItem) {
    if (this.categories == null) {
      this.categories = new ArrayList<>();
    }
    this.categories.add(categoriesItem);
    return this;
  }

  /**
   * Get categories
   * @return categories
  */
  @Valid

  public List<CategoryResponseModel> getCategories() {
    return categories;
  }

  public void setCategories(List<CategoryResponseModel> categories) {
    this.categories = categories;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CategoryListResponseModel categoryListResponseModel = (CategoryListResponseModel) o;
    return Objects.equals(this.categories, categoryListResponseModel.categories);
  }

  @Override
  public int hashCode() {
    return Objects.hash(categories);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CategoryListResponseModel {\n");

    sb.append("    categories: ").append(toIndentedString(categories)).append("\n");
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

