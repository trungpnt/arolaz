package com.ecommerce.arolaz.Inventory.Service;

import com.amazonaws.services.alexaforbusiness.model.NotFoundException;
import com.ecommerce.arolaz.Inventory.Model.Inventory;
import com.ecommerce.arolaz.Inventory.Repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InventoryServiceImpl implements InventoryService {
    @Autowired
    private InventoryRepository inventoryRepository;

    @Override
    public Optional<Inventory> findBySizeAndColor(String proId, String sizeId, String colorId) {
        Optional<Inventory> inventory = inventoryRepository.findByProductIdAndProductSizeIdAndColorId(proId,sizeId,colorId);
        if(!inventory.isPresent()){
            throw new NotFoundException("Inventory with given properties not found !");
        }
        return inventory;
    }


}
