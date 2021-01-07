package com.ecommerce.arolaz.Inventory.Repository;

import com.ecommerce.arolaz.Inventory.Model.Inventory;
import org.bson.types.ObjectId;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventoryRepository extends PagingAndSortingRepository<Inventory, ObjectId> {
    Optional<Inventory> findByProductIdAndProductSizeIdAndColorId(String proId, String sizeId, String colorId);

}
