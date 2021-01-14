//package com.ecommerce.arolaz.Repository;
//
//
//import com.ecommerce.arolaz.Product.Model.Product;
//import com.ecommerce.arolaz.Product.Model.ProductDynamicQuery;
//import com.ecommerce.arolaz.Product.Service.ProductService;
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.List;
//
//@RunWith(SpringRunner.class)
//public class ProductRepositoryTest {
//
//    @Autowired
//    private ProductService productService;
//
//    @Test
//    public void query_combinedQuery_shouldReturnList() {
//        // Given
//        // DB with default products
//        final String productName = "Nike Jacket";
//        final String categoryName = "Nike";
//        final String colorName = "red";
//        final Double price = 200000.0;
//
//        final ProductDynamicQuery dynamicQuery = new ProductDynamicQuery();
//        dynamicQuery.setProductName(productName);
//        dynamicQuery.setCategoryName(categoryName);
//        dynamicQuery.setColorName(colorName);
//        dynamicQuery.setPrice(price);
//
//        // When
//        final List<Product> productList = productService.query(dynamicQuery);
//
//        // Then
//        final int expectedCount = 1;
//        Assert.assertEquals(expectedCount, productList.size());
//    }
//}
