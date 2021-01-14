package com.ecommerce.arolaz.Inventory.Service;


import com.ecommerce.arolaz.Inventory.Model.Inventory;

import java.util.Optional;

public interface InventoryService {
    Optional<Inventory> findBySizeAndColor(String proId, String sizeId, String colorId);
    Inventory addNewInventory(Inventory inventory);
    String deleteByProductId(String productId);
    void deleteInventory(Inventory inventory);
    Optional<Inventory> findByProductIdAndSizeIdAndColorId(String proId, String sizeId, String colorId);
}
