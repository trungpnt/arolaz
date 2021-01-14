package com.ecommerce.arolaz.Inventory.Service;

import com.ecommerce.arolaz.Utils.ExceptionHandlers.InventoryNotFoundException;
import com.ecommerce.arolaz.Inventory.Model.Inventory;
import com.ecommerce.arolaz.Inventory.Repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryServiceImpl implements InventoryService {
    @Autowired
    private InventoryRepository inventoryRepository;

    @Override
    public Optional<Inventory> findBySizeAndColor(String proId, String sizeId, String colorId) {
        Optional<Inventory> inventory = inventoryRepository.findByProductIdAndSizeIdAndColorId(proId,sizeId,colorId);
        if(!inventory.isPresent()){
            throw new InventoryNotFoundException(String.format("Inventory with '%s' and '%s' and '%s' not found !", proId,sizeId,colorId));
        }
        return inventory;
    }

    @Override
    public Inventory addNewInventory(Inventory inventory) {
        return inventoryRepository.save(inventory);
    }

    @Override
    public String deleteByProductId(String productId){
        List<Inventory> tryFind = inventoryRepository.findByProductId(productId);
        if(tryFind.size() == 0){
            throw new InventoryNotFoundException(String.format("Inventory with '%s' not found !", productId));
        }
        inventoryRepository.deleteByProductId(productId);
        return "DELETED";
    }

    @Override
    public void deleteInventory(Inventory inventory) {
        inventoryRepository.delete(inventory);
    }

    @Override
    public Optional<Inventory> findByProductIdAndSizeIdAndColorId(String proId, String sizeId, String colorId) {
        return inventoryRepository.findByProductIdAndSizeIdAndColorId(proId, sizeId, colorId);
    }
}
