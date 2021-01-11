//package com.ecommerce.arolaz.Product.RestfulParams.core;
//
//import com.ecommerce.arolaz.Product.RestfulParams.exceptions.WrongQueryParam;
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//import org.springframework.data.mongodb.core.query.Criteria;
//import org.springframework.data.mongodb.core.query.Query;
//import org.springframework.stereotype.Component;
//
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//
//import static com.ecommerce.arolaz.Product.RestfulParams.domain.Filter.*;
//
//@Component
//public class FilterApplier {
//
//  public static final String FIELD_DECIMETER = ",";
//
//  public static void applyRestApiQueries(Query query, Map<String, String> restApiQueries) {
//    applySelectQueryParam(query, restApiQueries);
//  }
//
//  public static void applyPageQueryParam(Query query, Map<String, String> restApiQueries) {
//    try {
//      if (restApiQueries.get(PAGE.getCode()) != null) {
//        String pageQueryParam = restApiQueries.get(PAGE.getCode());
//        long skip = Long.parseLong(pageQueryParam);
//        if (skip < 0) {
//          throw new NumberFormatException();
//        }
//        query.skip(skip);
//      }
//    } catch (NumberFormatException pageParamExc) {
//      throw new WrongQueryParam("Page param must be greater than or equal to 0");
//    }
//  }
//
//  public static void applyPageSizeQueryParam(Query query, Map<String, String> restApiQueries) {
//    try {
//      if (restApiQueries.get(PAGE_SIZE.getCode()) != null) {
//        String pageSizeQueryParam = restApiQueries.get(PAGE_SIZE.getCode());
//        int limit = Integer.parseInt(pageSizeQueryParam);
//        if (limit < 0) {
//          throw new NumberFormatException();
//        }
//        query.limit(limit);
//      }
//    } catch (NumberFormatException pageParamExc) {
//      throw new WrongQueryParam("PageSize param must greater than or equal to 0");
//    }
//  }
//
////  public static void applySelectQueryParam(Query query, Map<String, String> restApiQueries) {
////    List<String> selectedFields = new ArrayList<>();
////    List<String> selectedFieldProps = new ArrayList<>();
////
////
////    for (Map.Entry mapElement : restApiQueries.entrySet()) {
////      selectedFields.add(mapElement.getValue().toString());
////      selectedFieldProps.add((String)mapElement.getKey());
////
////      query.addCriteria(Criteria.where())
////    }
////
////
////      for (String selectedField : selectedFields) {
////        query.fields().include(selectedField);
////      }
////  }
//
//}
//
