package com.ecommerce.arolaz.Color.Service;


import com.ecommerce.arolaz.Color.Model.Color;
import com.ecommerce.arolaz.Color.Repository.ColorRepository;
import com.ecommerce.arolaz.Utils.ExceptionHandlers.ColorNameAlreadyExistsException;
import com.ecommerce.arolaz.Utils.ExceptionHandlers.ColorNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ColorServiceImpl implements ColorService {
    @Autowired
    private ColorRepository repository;

    @Override
    public Optional<Color> addNewColor(Color color) {
        if(!existsByColorName(color.getColorName())){
            return Optional.of(repository.save(color));
        }
        throw new ColorNameAlreadyExistsException(String.format("Color with %s already exists",color.getColorName()));
    }

    @Override
    public Optional<Color> findByColorName(String colorName) {
        Optional<Color> find = repository.findByColorName(colorName);
        if(!find.isPresent()){
            throw new ColorNotFoundException(String.format("Color with '%s' not found ",colorName));
        }
        return find;
    }

    @Override
    public boolean existsByColorName(String colorName){
        return repository.existsByColorName(colorName);
    }

    @Override
    public List<Color> findAll() {
        return repository.findAll();
    }

}
