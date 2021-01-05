package com.ecommerce.arolaz.Inventory.Service;


import com.ecommerce.arolaz.Inventory.Model.Inventory;

import java.util.Optional;

public interface InventoryService {
    Optional<Inventory> findBySizeAndColor(String proId, String sizeId, String colorId);

}
