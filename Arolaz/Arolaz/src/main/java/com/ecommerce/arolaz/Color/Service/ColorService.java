package com.ecommerce.arolaz.Color.Service;

import com.ecommerce.arolaz.Color.Model.Color;
import com.ecommerce.arolaz.Color.RequestResponseModels.ColorResponseModel;
import com.ecommerce.arolaz.ProductSize.Model.ProductSize;

import java.util.List;
import java.util.Optional;

public interface ColorService {
    Optional<Color> addNewColor(Color color);
    Optional<Color> findByColorName(String colorName);
    List<Color> findAll();
    boolean existsByColorName (String colorName);
}
