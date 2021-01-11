package com.ecommerce.arolaz.Product.RestfulParams.core;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static com.ecommerce.arolaz.Product.RestfulParams.domain.Filter.*;

@Component
public class FilterCollector {

  public static Map<String, String> collectRestApiParams(Map<String, String> filters) {
    Map<String, String> restApiQueries = new HashMap<>();
    collectSelectFilter(restApiQueries, filters);

    return restApiQueries;
  }

  private static void collectPageSizeFilter(Map<String, String> restApiQueries,
      Map<String, String> filters) {
    if (filters.containsKey(PAGE_SIZE.getCode())) {
      restApiQueries.put(PAGE_SIZE.getCode(), filters.get(PAGE_SIZE.getCode()));
    }
  }

  private static void collectSelectFilter(Map<String, String> restApiQueries,
      Map<String, String> filters) {
    if (filters.containsKey(SELECT.getCode())) {
      restApiQueries.put(SELECT.getCode(), filters.get(SELECT.getCode()));
    }
  }

  private static void collectPageFilter(Map<String, String> restApiQueries,
      Map<String, String> filters) {
    if (filters.containsKey(PAGE.getCode())) {
      restApiQueries.put(PAGE.getCode(), filters.get(PAGE.getCode()));
    }
  }


}
