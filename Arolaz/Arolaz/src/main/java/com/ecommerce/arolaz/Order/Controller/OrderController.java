package com.ecommerce.arolaz.Order.Controller;

import com.ecommerce.arolaz.Inventory.Service.InventoryService;
import com.ecommerce.arolaz.Utils.ExceptionHandlers.InsufficientInventoryQuantityException;
import com.ecommerce.arolaz.Utils.ExceptionHandlers.TokenUserIdNotFoundException;
import com.ecommerce.arolaz.Utils.ExceptionHandlers.UserNotFoundException;
import com.ecommerce.arolaz.Inventory.Model.Inventory;
import com.ecommerce.arolaz.Inventory.Repository.InventoryRepository;
import com.ecommerce.arolaz.Order.Model.Order;
import com.ecommerce.arolaz.Order.RequestResponseModels.CreateOrderRequestModel;
import com.ecommerce.arolaz.Order.RequestResponseModels.OrderIdResponseModel;
import com.ecommerce.arolaz.Order.RequestResponseModels.OrderResponseModel;
import com.ecommerce.arolaz.Order.RequestResponseModels.PagedOrderResponseModel;
import com.ecommerce.arolaz.Order.Service.OrderService;
import com.ecommerce.arolaz.OrderDetails.Model.OrderDetails;
import com.ecommerce.arolaz.OrderDetails.RequestResponseModels.CreateOrderDetailsRequestModel;
import com.ecommerce.arolaz.OrderDetails.Service.OrderDetailsService;
import com.ecommerce.arolaz.Product.Service.ProductService;
import com.ecommerce.arolaz.ProductSize.Model.ProductSize;
import com.ecommerce.arolaz.ProductSize.Service.ProductSizeService;
import com.ecommerce.arolaz.Utils.Security.JwtProvider;
import com.ecommerce.arolaz.SecurityUser.Model.SecurityUser;
import com.ecommerce.arolaz.SecurityUser.Service.SecurityUserService;
import com.ecommerce.arolaz.Size.Model.Size;
import com.ecommerce.arolaz.Size.Service.SizeService;
import com.ecommerce.arolaz.UnregisteredUsers.Model.UnregisteredUser;
import com.ecommerce.arolaz.UnregisteredUsers.RequestResponseModels.CreateUnregisteredUserModel;
import com.ecommerce.arolaz.UnregisteredUsers.RequestResponseModels.UnregisteredUserOrderRequestModel;
import com.ecommerce.arolaz.UnregisteredUsers.Service.UnregisteredUserService;
import com.ecommerce.arolaz.Utils.CustomizedPagingResponseModel;
import com.ecommerce.arolaz.Utils.TokenValidator;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderDetailsService orderDetailsService;

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private UnregisteredUserService unregisteredUserService;

    @Autowired
    private ProductService productService;

    @Autowired
    private SecurityUserService securityUserService;

    @Autowired
    private ProductSizeService productSizeService;

    @Autowired
    private SizeService sizeService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private TokenValidator tokenValidator;

    @Autowired
    private InventoryService inventoryService;

    private OrderDetails toOrderDetailsDomain (String orderId){
        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setOrderId(orderId);
        return orderDetails;
    }
    /**
     * Api for Unregistered users to submit orders
     * @param unregisteredUserOrderRequestModel
     * @return The Id of created Order
     */
    @PostMapping(path = "/order/unregistered")
    public OrderIdResponseModel addNewOrder(@Valid @RequestBody UnregisteredUserOrderRequestModel unregisteredUserOrderRequestModel ){

        /**
         * Extracting 2 corresponding objects
         * */
        CreateUnregisteredUserModel createUnregisteredUserModel = unregisteredUserOrderRequestModel.getUnregisteredUser();
        CreateOrderRequestModel createOrderRequestModel = unregisteredUserOrderRequestModel.getCreateOrderRequestModel();

        /**
         * Persist Unregistered User
         * */
        UnregisteredUser toPersistUnregisteredUser = modelMapper.map(createUnregisteredUserModel,UnregisteredUser.class);
        UnregisteredUser afterSaved =  unregisteredUserService.addNewUnregisteredUser(toPersistUnregisteredUser);

        /**
         * Order process begins
         *
         * */
        Order toPersistOrder = new Order();
        toPersistOrder.setUnregisteredUserId(afterSaved.getUnregisteredUserId().toString());
        //toPersistOrder.setNote(createOrderRequestModel.getNote());
        toPersistOrder.setAddress(createOrderRequestModel.getAddress());

        Order toGetOrderId = orderService.addOrder(toPersistOrder);
        String orderId = toGetOrderId.getOrderId().toString();

        /**
         * Order Details Process begins
         * Calculating the TotalQuantity and TotalPrice to persist into Order
         * */
        double totalPrice = 0;
        int totalQuantity = 0;

        List<CreateOrderDetailsRequestModel> orderDetailsList = createOrderRequestModel.getCreateOrderDetails();
        for(int i  = 0, n = orderDetailsList.size(); i < n; i++){
            OrderDetails orderDetails = new OrderDetails();

            /***
             * Place each extracted values into variables
             */
            String productId = orderDetailsList.get(i).getProductId();
            String sizeId = orderDetailsList.get(i).getSizeId();
            String colorId = orderDetailsList.get(i).getColorId();
            int quantity = orderDetailsList.get(i).getQuantity();

            /**
             * Get sizeName for this sizeId
             * @param sizeId
             * @return sizeName
             * */
            ObjectId sizeObjectId = new ObjectId(sizeId);
            Optional<Size> sizeFound = sizeService.findBySizeId(sizeObjectId);
            String sizeName = sizeFound.get().getSizeName();

            /**
             * Get unitPrice for this product
             * @param productId,sizeId
             * @return unitPrice
             * */
            Optional<ProductSize> productSizeFound = productSizeService.findByProductIdAndSizeName(productId,sizeName);
            double unitPrice = productSizeFound.get().getPrice();

            /**
             * Calculate the price for calculatedPrice field
             * */
            double calculatedPrice = quantity * unitPrice;

            /**
             * Check each product's quantity against one in Inventory
             * Update remaining quantity
             *
             * @param productId,sizeId,colorId
             * @return quantity
             * */
            Optional<Inventory> inventoryFound =  inventoryService.findByProductIdAndSizeIdAndColorId(productId,sizeId,colorId);
            int inventoryQuantity = inventoryFound.get().getQuantity();

            /**
             * Ensure sufficient quantity
             * */
            if( quantity > inventoryQuantity ){
                throw new InsufficientInventoryQuantityException(String.format("The product with %s, %s, %s quantity exceeds inventory's one",productId, sizeId, colorId));
            }

            int newInventoryQuantity = inventoryQuantity - quantity;

            /**
             *
             * Update new Inventory's quantity
             * */
            inventoryFound.get().setQuantity(newInventoryQuantity);
            inventoryService.addNewInventory(inventoryFound.get());

            /**
             * Creating and persisting order details model
             * */
            orderDetails.setProductId(productId);
            orderDetails.setOrderId(orderId);
            orderDetails.setCalculatedPrice(calculatedPrice);
            orderDetails.setProductSizeId(sizeId);
            orderDetails.setColorId(colorId);
            orderDetails.setOrderId(orderId);
            orderDetails.setUnitPrice(unitPrice);
            orderDetails.setQuantity(quantity);

            orderDetailsService.addOrderDetails(orderDetails);

            /**
             *Preparing totalPrice and totalQuantity for persisting Order
             * */
            totalPrice += calculatedPrice;
            totalQuantity += quantity;
        }
        toGetOrderId.setTotalPrice(totalPrice);
        toGetOrderId.setTotalQuantity(totalQuantity);
        orderService.addOrder(toGetOrderId);

        return new OrderIdResponseModel(orderId);

    }


    /**
     * Registered user submitting orders
     * @param createOrderRequestModel
     * @return Id of Order
     */
    @PreAuthorize("hasAuthority('USER')")
    @PostMapping(path = "/order")
    public OrderIdResponseModel addNewOrder(HttpServletRequest request, @RequestHeader(value = "Authorization",required = true) String headerVal, @Valid @RequestBody CreateOrderRequestModel createOrderRequestModel ) {
        /***
         * decode TOKEN's and validate against the details
         */
        String token = headerVal.substring(headerVal.indexOf(" "));
        tokenValidator.validateTokenUserAuthorization(token);
        String userId = jwtProvider.getUserId(token);
        Optional<SecurityUser> user = securityUserService.findByPhoneNumber(jwtProvider.getPhone(token));

        if (!user.isPresent()) {
            throw new RuntimeException("USER NOT FOUND, PLEASE CHECK YOUR INFORMATION BEFORE ORDERING");
        }

        /**
         * Order process begins
         *
         * */
        Order toPersistOrder = new Order();
        toPersistOrder.setSecurityUserId(userId);
        toPersistOrder.setNote(createOrderRequestModel.getNote());
        toPersistOrder.setAddress(createOrderRequestModel.getAddress());

        Order toGetOrderId = orderService.addOrder(toPersistOrder);
        String orderId = toGetOrderId.getOrderId().toString();

        /**
         * Order Details Process begins
         * Calculating the TotalQuantity and TotalPrice to persist into Order
         * */
        double totalPrice = 0;
        int totalQuantity = 0;

        List<CreateOrderDetailsRequestModel> orderDetailsList = createOrderRequestModel.getCreateOrderDetails();

        for(int i  = 0, n = orderDetailsList.size(); i < n; i++){

            OrderDetails orderDetails = new OrderDetails();

            /***
             * Place each extracted values into variables
             */
            String productId = orderDetailsList.get(i).getProductId();
            String sizeId = orderDetailsList.get(i).getSizeId();
            String colorId = orderDetailsList.get(i).getColorId();
            int quantity = orderDetailsList.get(i).getQuantity();

            /**
             * Retrieving sizeName for this sizeId
             * */
            Optional<Size> sizeFound = sizeService.findBySizeId(new ObjectId(sizeId));
            String sizeName = sizeFound.get().getSizeName();

            /**
             * Retrieving the unit price for this product
             * */
            Optional<ProductSize> productSizeFound = productSizeService.findByProductIdAndSizeName(productId,sizeName);
            double unitPrice = productSizeFound.get().getPrice();

            /**
             * Calculate the price for calculatedPrice field
             * */
            double calculatedPrice = quantity * unitPrice;

            /**
             * Checking each product's quantity against one in Inventory
             * Updating remaining quantity
             * */
            Optional<Inventory> inventoryFound =  inventoryService.findByProductIdAndSizeIdAndColorId(productId,sizeId,colorId);
            int inventoryQuantity = inventoryFound.get().getQuantity();

            if( quantity > inventoryQuantity ){
                throw new RuntimeException("The product quantity exceeds inventory's");
            }

            int newInventoryQuantity = inventoryQuantity - quantity;

            /**
             * Update new Inventory's quantity
             * */
            inventoryFound.get().setQuantity(newInventoryQuantity);
            inventoryService.addNewInventory(inventoryFound.get());

            /**
             * Creating and persisting order details model
             * */
            orderDetails.setProductId(productId);
            orderDetails.setOrderId(orderId);
            orderDetails.setCalculatedPrice(calculatedPrice);
            orderDetails.setProductSizeId(sizeId);
            orderDetails.setColorId(colorId);
            orderDetails.setOrderId(orderId);
            orderDetails.setUnitPrice(unitPrice);
            orderDetails.setQuantity(quantity);

            orderDetailsService.addOrderDetails(orderDetails);

            /**
             *Preparing totalPrice and totalQuantity for persisting Order
             * */
            totalPrice += calculatedPrice;
            totalQuantity += quantity;
        }
        toGetOrderId.setTotalPrice(totalPrice);
        toGetOrderId.setTotalQuantity(totalQuantity);
        orderService.addOrder(toGetOrderId);

        return new OrderIdResponseModel(orderId);
    }

    @GetMapping(path = "/order/{orderId}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<OrderResponseModel> getSingleOrder(@PathVariable(value = "orderId") ObjectId orderId) {

        Optional<Order> order = orderService.findByOrderId(orderId);
        return new ResponseEntity<OrderResponseModel>(toDto(order),HttpStatus.OK);
    }

    private OrderResponseModel toDto (Optional<Order> order){
        OrderResponseModel orderResponseModel = new OrderResponseModel();
        orderResponseModel.setOrderId(order.get().getOrderId().toString());
        orderResponseModel.setTotalPrice(order.get().getTotalPrice());
        orderResponseModel.setTotalQuantity(order.get().getTotalQuantity());
        orderResponseModel.setAddress(order.get().getAddress());
        orderResponseModel.setNote(order.get().getNote());
        orderResponseModel.setProducts(order.get().getOrderDetails());
        return orderResponseModel;
    }

    @GetMapping(path = "/user/order")
    @PreAuthorize("hasAuthority('USER')")
    public CustomizedPagingResponseModel<PagedOrderResponseModel> getOrdersByUserToken(
            @RequestParam("page") Integer page,
            @RequestParam("rows") Integer rows,
            Pageable pageable,
            HttpServletRequest request, @RequestHeader(value = "Authorization") String headerVal) {

        Page<Order> orderPage;
        String token = headerVal.substring(headerVal.indexOf(" "));

        tokenValidator.validateTokenUserAuthorization(token);

        String securityUserId = jwtProvider.getUserId(token);

        if(securityUserId == null){
            throw new TokenUserIdNotFoundException(String.format("USER WITH {userId} NOT FOUND",securityUserId));
        }

        Optional<SecurityUser> user = securityUserService.findByPhoneNumber(jwtProvider.getPhone(token));

        if (!user.isPresent()) {
            throw new UserNotFoundException("USER NOT FOUND !");
        }

        orderPage = orderService.findBySecurityUserId(securityUserId,pageable);

        List<PagedOrderResponseModel> pagedOrderResponseModels = orderPage.getContent().stream().map(
                Order -> toDtoPage(Order)).collect(Collectors.toList());

        CustomizedPagingResponseModel customizedPagingResponseModel = new CustomizedPagingResponseModel();
        customizedPagingResponseModel.setPagingData(pagedOrderResponseModels);
        customizedPagingResponseModel.setTotalPage(orderPage.getTotalPages());
        return customizedPagingResponseModel;
    }
    
    private OrderResponseModel toDto(Order order) {
        return new OrderResponseModel(order.getOrderId().toString(),order.getTotalPrice(),order.getTotalQuantity(),order.getAddress(),order.getNote(),order.getOrderDetails());
    }

    private PagedOrderResponseModel toDtoPage(Order order){
        return new PagedOrderResponseModel(order.getOrderId().toString(),order.getTotalPrice(),order.getTotalQuantity(),order.getAddress(),order.getNote());
    }
}
