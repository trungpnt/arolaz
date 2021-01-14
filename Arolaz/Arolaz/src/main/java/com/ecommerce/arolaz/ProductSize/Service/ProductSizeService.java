package com.ecommerce.arolaz.ProductSize.Service;

import com.ecommerce.arolaz.ProductSize.Model.ProductSize;
import com.ecommerce.arolaz.ProductSize.RequestResponseModels.CreateProductSizeRequestModel;
import com.ecommerce.arolaz.ProductSize.RequestResponseModels.ProductSizeResponseModel;

import java.util.List;
import java.util.Optional;

public interface ProductSizeService {
    ProductSize addNewProductSize(ProductSize productSize);
    Optional<ProductSize> findByProductIdAndSizeNameAndPrice(String proId, String sizeName, Double price);
    Optional<ProductSize> findByProductIdAndSizeName(String proId,String sizeName);
    List<ProductSize> findByProductId(String proId);
    String deleteByProductId(String proId);
    List<ProductSize> findAll();
}
