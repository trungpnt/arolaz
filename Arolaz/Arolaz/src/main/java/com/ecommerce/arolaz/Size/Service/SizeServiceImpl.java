package com.ecommerce.arolaz.Size.Service;

import com.ecommerce.arolaz.Utils.ExceptionHandlers.SizeNotFoundException;
import com.ecommerce.arolaz.Size.Model.Size;
import com.ecommerce.arolaz.Size.Repository.SizeRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SizeServiceImpl implements SizeService {
    @Autowired
    private SizeRepository sizeRepository;

    @Override
    public Optional<Size> findBySizeName(String sizeName) {
        Optional<Size> foundSize = sizeRepository.findBySizeName(sizeName);
        if(!foundSize.isPresent()){
            throw new SizeNotFoundException(String.format("Size with '%s' not found",sizeName));
        }
        return foundSize;
    }

    @Override
    public Optional<Size> findBySizeId(ObjectId sizeId) {
        Optional<Size> foundSize = sizeRepository.findById(sizeId);
        if(!foundSize.isPresent()){
            throw new SizeNotFoundException(String.format("Size with '%s' not found",sizeId.toString()));
        }
        return foundSize;
    }

    @Override
    public boolean existsBySizeName(String sizeName){
        return sizeRepository.existsBySizeName(sizeName);
    }

    @Override
    public Optional<Size> addNewSize(Size size){
        if(!existsBySizeName(size.getSizeName())){
            return Optional.of(sizeRepository.save(size));
        }
        return Optional.empty();
    }

    @Override
    public List<Size> findAll(){
        return sizeRepository.findAll();
    }

}
