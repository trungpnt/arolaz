package com.ecommerce.arolaz.ProductSize.Service;

import com.ecommerce.arolaz.ProductSize.Model.ProductSize;
import com.ecommerce.arolaz.ProductSize.RequestResponseModels.CreateProductSizeRequestModel;
import com.ecommerce.arolaz.ProductSize.RequestResponseModels.ProductSizeResponseModel;

import java.util.Optional;

public interface ProductSizeService {
    ProductSize addNewProductSize(ProductSize productSize);
    Optional<ProductSize> findByGivenProperties(String proId, String sizeName, Double price);

}
