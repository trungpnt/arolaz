package com.ecommerce.arolaz.Brand.Service;

import com.ecommerce.arolaz.Brand.Model.Brand;
import com.ecommerce.arolaz.Brand.Repository.BrandRepository;
import com.ecommerce.arolaz.Utils.ExceptionHandlers.BrandNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BrandServiceImpl implements BrandService{
    @Autowired
    private BrandRepository brandRepository;

    @Override
    public Optional<Brand> findByBrandName(String brandName) {
        Optional<Brand> foundBrand = brandRepository.findByBrandName(brandName);
        if(!foundBrand.isPresent()){
            throw new BrandNotFoundException(String.format("The brand with '%s' does not exists ",brandName));
        }
        return foundBrand;
    }

    @Override
    public Brand addNew(Brand brand){
        return brandRepository.save(brand);
    }

    @Override
    public List<Brand> getAllBrands(){
        return brandRepository.findAll();
    }
}
