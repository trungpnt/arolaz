package com.ecommerce.arolaz.Brand.Service;

import com.ecommerce.arolaz.Brand.Model.Brand;

import java.util.List;
import java.util.Optional;

public interface BrandService {
    Optional<Brand> findByBrandName(String brandName);
    Brand addNew(Brand brand);
    List<Brand> getAllBrands();
}
