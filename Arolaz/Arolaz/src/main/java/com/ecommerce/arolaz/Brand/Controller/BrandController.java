package com.ecommerce.arolaz.Brand.Controller;


import com.ecommerce.arolaz.Brand.Model.Brand;
import com.ecommerce.arolaz.Brand.RequestResponseModels.BrandResponseModel;
import com.ecommerce.arolaz.Brand.RequestResponseModels.CreateBrandRequestModel;
import com.ecommerce.arolaz.Brand.Service.BrandService;
import com.ecommerce.arolaz.Utils.ObjectCreationSuccessResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class BrandController {
    @Autowired
    private BrandService brandService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping(path="/brand")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ObjectCreationSuccessResponse> createBrand(@Valid @RequestBody CreateBrandRequestModel createBrandRequestModel) {
        Brand brand = modelMapper.map(createBrandRequestModel,Brand.class);
        ObjectCreationSuccessResponse result = new ObjectCreationSuccessResponse();
        Optional<Brand> found = brandService.findByBrandName(brand.getBrandName());

        if(found.isPresent() ){
            result.setId("BRAND WITH THE SAME NAME ALREADY EXISTS");
            result.setResponseCode(HttpStatus.FORBIDDEN.value());
            return new ResponseEntity<ObjectCreationSuccessResponse>(result,HttpStatus.FORBIDDEN);
        }
        Optional<Brand> persistBrand = brandService.addNew(brand);
        result.setId(persistBrand.get().getBrandId().toString());
        result.setResponseCode(HttpStatus.CREATED.value());
        return new ResponseEntity<ObjectCreationSuccessResponse>(result,HttpStatus.CREATED);
    }

    @GetMapping(path = "/brand")
    @ResponseStatus(HttpStatus.OK)
    public List<BrandResponseModel> getAllBrands() {
        List<BrandResponseModel> brandResponseModels;
        brandResponseModels = brandService.getAllBrands().stream().map(
                brand -> toDto(brand)
        ).collect(Collectors.toList());

        return brandResponseModels;
    }

    public BrandResponseModel toDto(Brand brand){
        return new BrandResponseModel(brand.getBrandId().toString(),brand.getBrandName());
    }

    /**
     * Find brand by brandName
     * */
    @GetMapping(path = "/brand/{brandName}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BrandResponseModel> getBrandByName(@PathVariable( value = "brandName") String brandName) {
        BrandResponseModel brandResponseModel = new BrandResponseModel();

        Optional<Brand> brand = brandService.findByBrandName(brandName);

        brandResponseModel.setId(brand.get().getBrandId().toString());
        brandResponseModel.setBrandName(brand.get().getBrandName());

        return new ResponseEntity<>(brandResponseModel,HttpStatus.OK);
    }

}
