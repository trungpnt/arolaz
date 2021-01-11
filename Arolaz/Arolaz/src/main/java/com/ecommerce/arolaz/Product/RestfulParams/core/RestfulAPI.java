package com.ecommerce.arolaz.Product.RestfulParams.core;

import java.util.Map;

public class RestfulAPI {

  public static Map<String, String> collectRestApiParams(Map<String, String> filters) {
    return FilterCollector.collectRestApiParams(filters);
  }

}
