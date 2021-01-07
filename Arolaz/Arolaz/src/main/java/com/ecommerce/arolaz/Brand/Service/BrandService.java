package com.ecommerce.arolaz.Brand.Service;

import com.ecommerce.arolaz.Brand.Model.Brand;

import java.util.Optional;

public interface BrandService {
    Optional<Brand> findByBrandName(String brandName);
}
