package com.ecommerce.arolaz.Inventory.Service;

import com.ecommerce.arolaz.utils.ExceptionHandlers.InventoryNotFoundException;
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
            throw new InventoryNotFoundException(String.format("Inventory with '%s' and '%s' and '%s' not found !", proId,sizeId,colorId));
        }
        return inventory;
    }

    @Override
    public Inventory addNewInventory(Inventory inventory) {
        return inventoryRepository.save(inventory);
    }


}
