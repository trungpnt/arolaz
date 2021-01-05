package com.ecommerce.arolaz.Arolaz.Inventory.Service;


import com.ecommerce.arolaz.Arolaz.Inventory.Model.Inventory;

import java.util.Optional;

public interface InventoryService {
    Optional<Inventory> findBySizeAndColor(String proId, String sizeId, String colorId);

}
