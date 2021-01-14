package com.ecommerce.arolaz.Color.Controller;


import com.ecommerce.arolaz.Color.Model.Color;
import com.ecommerce.arolaz.Color.Repository.ColorRepository;
import com.ecommerce.arolaz.Color.RequestResponseModels.ColorResponseModel;
import com.ecommerce.arolaz.Color.RequestResponseModels.CreateColorRequestModel;
import com.ecommerce.arolaz.Color.Service.ColorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ColorController {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ColorService service;

    @GetMapping("/color")
    public ResponseEntity<List<ColorResponseModel>> getAllColors(){
        List<Color> colors = service.findAll();
        return new ResponseEntity<>(buildList(colors), HttpStatus.OK);
    }

    private ColorResponseModel toColorResponseModel(Color color){
        return new ColorResponseModel(color.getColorId().toString(), color.getColorName());
    }

    private List<ColorResponseModel> buildList(List<Color> colors){
        List<ColorResponseModel> responseModels = new ArrayList<>();
        for(int i = 0, n = colors.size(); i < n; i++){
            responseModels.add(toColorResponseModel(colors.get(i)));
        }
        return responseModels;
    }

    @PostMapping("/color")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ColorResponseModel> addNewColor(CreateColorRequestModel createColorRequestModel){
        String colorName = createColorRequestModel.getColorName();

        Optional<Color> colorOptional = service.findByColorName(createColorRequestModel.getColorName());
        if(colorOptional.isPresent()){
            throw new RuntimeException("Color name already exists !".toUpperCase());
        }
        Color persistColor = modelMapper.map(createColorRequestModel,Color.class);
        service.addNewColor(persistColor);
        return new ResponseEntity<>(toColorResponseModel(persistColor),HttpStatus.CREATED);
    }

}
