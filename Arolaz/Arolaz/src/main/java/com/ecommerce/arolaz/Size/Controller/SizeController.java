package com.ecommerce.arolaz.Size.Controller;


import com.ecommerce.arolaz.Size.Model.Size;
import com.ecommerce.arolaz.Size.RequestResponseModels.CreateSizeRequestModel;
import com.ecommerce.arolaz.Size.RequestResponseModels.SizeResponseModel;
import com.ecommerce.arolaz.Size.Service.SizeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class SizeController {

    @Autowired
    private SizeService sizeService;

    @GetMapping("/size")
    public ResponseEntity<List<SizeResponseModel>> getAllSizes(){
        List<Size> findAllSizes = sizeService.findAll();
        return new ResponseEntity<>(buildSizeResponseModelList(findAllSizes), HttpStatus.OK);
    }

    @PostMapping("/size")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<SizeResponseModel> addNewSize(CreateSizeRequestModel createSizeRequestModel){

        Size toPersist = new Size();
        toPersist.setSizeName(createSizeRequestModel.getSizeName());
        Optional<Size> sizeSaved =  sizeService.addNewSize(toPersist);
        return new ResponseEntity<>(toSizeResponseModel(sizeSaved.get()),HttpStatus.OK);
    }

    private SizeResponseModel toSizeResponseModel(Size size){
        return new SizeResponseModel(size.getSizeId().toString(),size.getSizeName());
    }

    private List<SizeResponseModel> buildSizeResponseModelList(List<Size> size){
        return size.stream().map(eachSize -> toSizeResponseModel(eachSize)).collect(Collectors.toList());
    }
}
