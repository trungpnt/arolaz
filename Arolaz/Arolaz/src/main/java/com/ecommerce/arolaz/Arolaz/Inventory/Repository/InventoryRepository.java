package com.ecommerce.arolaz.Arolaz.Inventory.Repository;

import com.ecommerce.arolaz.Arolaz.Inventory.Model.Inventory;
import org.bson.types.ObjectId;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventoryRepository extends PagingAndSortingRepository<Inventory, ObjectId> {
    Optional<Inventory> findByProductIdAndProductSizeIDAndColorId(String proId, String sizeId, String colorId);
}
