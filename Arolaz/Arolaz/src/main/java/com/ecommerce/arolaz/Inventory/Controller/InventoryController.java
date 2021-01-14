package com.ecommerce.arolaz.Inventory.Controller;



import com.ecommerce.arolaz.Inventory.Model.Inventory;
import com.ecommerce.arolaz.Inventory.RequestResponseModels.CreateInventoryRequestModel;
import com.ecommerce.arolaz.Inventory.RequestResponseModels.InventoryQuantityResponseModel;
import com.ecommerce.arolaz.Inventory.RequestResponseModels.InventoryResponseModel;
import com.ecommerce.arolaz.Inventory.Service.InventoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class InventoryController {
    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private ModelMapper modelMapper;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/inventory")
    public ResponseEntity<InventoryResponseModel> addNewInventory(@Valid @RequestBody CreateInventoryRequestModel createInventoryRequestModel){
        Inventory toPersist = modelMapper.map(createInventoryRequestModel,Inventory.class);
        Optional<Inventory> inventory = inventoryService.findBySizeAndColor(createInventoryRequestModel.getProductId(), createInventoryRequestModel.getProductSizeId(), createInventoryRequestModel.getColorId());
        if(inventory.isPresent()){
            throw new RuntimeException("Inventory with given props already exists !");
        }
        inventoryService.addNewInventory(toPersist);
        return new ResponseEntity<>(toInventoryResponseModel(toPersist),HttpStatus.CREATED);
    }

    @GetMapping("/inventory")
    public ResponseEntity<InventoryQuantityResponseModel> getQuantityByGivenProperties(@RequestParam(value = "ProductId") String productId, @RequestParam(value = "ProductSizeId") String productSizeId, @RequestParam(value = "ColorId") String colorId){
        Optional<Inventory> inventory = inventoryService.findBySizeAndColor(productId,productSizeId,colorId);
        if(!inventory.isPresent()){
            throw new RuntimeException("Inventory with given props not found !");
        }
        return new ResponseEntity<>(toInventoryQuantityResponseModel(inventory.get()), HttpStatus.OK);
    }

    private InventoryQuantityResponseModel toInventoryQuantityResponseModel(Inventory inventory){
        return new InventoryQuantityResponseModel(inventory.getInventoryId().toString(),inventory.getQuantity());
    }
    private InventoryResponseModel toInventoryResponseModel(Inventory inventory){
        return new InventoryResponseModel(inventory.getInventoryId().toString(), inventory.getProductId(), inventory.getSizeId(), inventory.getColorId(), inventory.getQuantity());
    }

}
