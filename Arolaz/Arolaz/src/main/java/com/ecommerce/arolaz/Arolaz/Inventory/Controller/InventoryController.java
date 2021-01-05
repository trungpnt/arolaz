package com.ecommerce.arolaz.Arolaz.Inventory.Controller;



import com.ecommerce.arolaz.Arolaz.Inventory.Model.Inventory;
import com.ecommerce.arolaz.Arolaz.Inventory.RequestResponseModels.CreateInventoryRequestModel;
import com.ecommerce.arolaz.Arolaz.Inventory.RequestResponseModels.InventoryResponseModel;
import com.ecommerce.arolaz.Arolaz.Inventory.Service.InventoryService;
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

//    @PreAuthorize("hasAuthority('ADMIN')")
//    @PostMapping("/inventory")
//    public ResponseEntity<InventoryResponseModel> createInventory(@Valid @RequestBody CreateInventoryRequestModel createInventoryRequestModel){
//
//    }

    @GetMapping("/inventory/{productId}/{productSizeId}/{colorId}")
    public ResponseEntity<Integer> getQuantityByGivenProperties(@PathVariable(value = "productId") String productId, @PathVariable(value = "productSizeId") String productSizeId, @PathVariable(value = "colorId") String colorId){
        Optional<Inventory> inventory = inventoryService.findBySizeAndColor(productId,productSizeId,colorId);
        return new ResponseEntity<>(inventory.get().getQuantity(), HttpStatus.OK);
    }

    //LATER
//    @PreAuthorize("hasAuthority('ADMIN')")
//    @PutMapping("/inventory")
//    public ResponseEntity<InventoryResponseModel> increaseInventoryQuantity(@Valid @RequestBody CreateInventoryRequestModel createInventoryRequestModel){
//        Inventory persist = modelMapper.map(createInventoryRequestModel,Inventory.class);
//
//    }

    private InventoryResponseModel toInventoryResponseModel(Inventory inventory){
        return new InventoryResponseModel(inventory.getInventoryId().toString(), inventory.getProductId(), inventory.getProductSizeId(), inventory.getColorId(), inventory.getQuantity());
    }

    //LATER
//    @PreAuthorize("hasAuthority('ADMIN')")
//    @PutMapping("/inventory")
//    public ResponseEntity<InventoryResponseModel> decreaseInventoryQuantity(@Valid @RequestBody CreateInventoryRequestModel createInventoryRequestModel){
//
//    }
}
