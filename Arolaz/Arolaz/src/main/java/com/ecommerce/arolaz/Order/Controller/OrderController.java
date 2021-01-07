package com.ecommerce.arolaz.Order.Controller;

import com.ecommerce.arolaz.ExceptionHandlers.InvalidTokenException;
import com.ecommerce.arolaz.ExceptionHandlers.TokenUserIdNotFoundException;
import com.ecommerce.arolaz.ExceptionHandlers.UserNotFoundException;
import com.ecommerce.arolaz.Inventory.Model.Inventory;
import com.ecommerce.arolaz.Inventory.Repository.InventoryRepository;
import com.ecommerce.arolaz.Order.Model.Order;
import com.ecommerce.arolaz.Order.RequestResponseModels.CreateOrderRequestModel;
import com.ecommerce.arolaz.Order.RequestResponseModels.OrderIdResponseModel;
import com.ecommerce.arolaz.Order.RequestResponseModels.OrderResponseModel;
import com.ecommerce.arolaz.Order.RequestResponseModels.PagedOrderResponseModel;
import com.ecommerce.arolaz.Order.Service.OrderService;
import com.ecommerce.arolaz.OrderDetails.Model.OrderDetails;
import com.ecommerce.arolaz.OrderDetails.Repository.OrderDetailsRepository;
import com.ecommerce.arolaz.OrderDetails.RequestResponseModels.CreateOrderDetailsRequestModel;
import com.ecommerce.arolaz.Product.Repository.ProductRepository;
import com.ecommerce.arolaz.Product.Service.ProductService;
import com.ecommerce.arolaz.ProductSize.Model.ProductSize;
import com.ecommerce.arolaz.ProductSize.Repository.ProductSizeRepository;
import com.ecommerce.arolaz.Security.JwtProvider;
import com.ecommerce.arolaz.SecurityUser.Model.SecurityUser;
import com.ecommerce.arolaz.SecurityUser.Service.SecurityUserService;
import com.ecommerce.arolaz.Size.Model.Size;
import com.ecommerce.arolaz.Size.Repository.SizeRepository;
import com.ecommerce.arolaz.UnregisteredUsers.Model.UnregisteredUser;
import com.ecommerce.arolaz.UnregisteredUsers.Repository.UnregisteredUserRepository;
import com.ecommerce.arolaz.UnregisteredUsers.RequestResponseModels.CreateUnregisteredUserModel;
import com.ecommerce.arolaz.UnregisteredUsers.RequestResponseModels.UnregisteredUserOrderRequestModel;
import com.ecommerce.arolaz.utils.CustomizedPagingResponseModel;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("api/")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderDetailsRepository orderDetailsRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private UnregisteredUserRepository unregisteredUserRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private SecurityUserService securityUserService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductSizeRepository productSizeRepository;

    @Autowired
    private SecurityUserService securityUserRepository;

    @Autowired
    private SizeRepository sizeRepository;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private InventoryRepository inventoryRepository;

    private ArrayList<OrderDetails> orderDetailsArrayList = new ArrayList<>();

    private OrderDetails toOrderDetailsDomain (String orderId){
        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setOrderId(orderId);
        return orderDetails;
    }

    /**
     *
     * @param unregisteredUserOrderRequestModel
     * Unregistered user submitting orders
     *
     */
    @PostMapping(path = "/order/unregistered")
    public OrderIdResponseModel addNewOrder(@Valid @RequestBody UnregisteredUserOrderRequestModel unregisteredUserOrderRequestModel ){

        /**
         * Extracting 2 corresponding objects
         * */
        CreateUnregisteredUserModel createUnregisteredUserModel = unregisteredUserOrderRequestModel.getUnregisteredUser();
        CreateOrderRequestModel createOrderRequestModel = unregisteredUserOrderRequestModel.getCreateOrderDetails();

        /**
         * Persist Unregistered User
         * */
        UnregisteredUser toPersistUnregisteredUser = modelMapper.map(createUnregisteredUserModel,UnregisteredUser.class);
        UnregisteredUser afterSaved =  unregisteredUserRepository.save(toPersistUnregisteredUser);

        /**
         * Order process begins
         *
         * */
        Order toPersistOrder = new Order();
        toPersistOrder.setUnregisteredUserId(afterSaved.getUnregisteredUserId().toString());
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
            ObjectId sizeObjectId = new ObjectId(sizeId);
            Size sizeFound = sizeRepository.findBySizeId(sizeObjectId);
            String sizeName = sizeFound.getSizeName();

            /**
             * Retrieving the unit price for this product
             * */
            Optional<ProductSize> productSizeFound = productSizeRepository.findByProductIdAndSizeName(productId,sizeName);
            double unitPrice = productSizeFound.get().getPrice();

            /**
             * Calculate the price for calculatedPrice field
             * */
            double calculatedPrice = quantity * unitPrice;

            /**
             * Checking each product's quantity against one in Inventory
             * Updating remaining quantity
             * */
            Optional<Inventory> inventoryFound =  inventoryRepository.findByProductIdAndProductSizeIdAndColorId(productId,sizeId,colorId);
            int inventoryQuantity = inventoryFound.get().getQuantity();

            if( quantity > inventoryQuantity ){
                throw new RuntimeException("The product quantity exceeds inventory's");
            }

            int newInventoryQuantity = inventoryQuantity - quantity;

            //Deletion flow ....
            //Inventory inventoryToDelete = new Inventory(productId,sizeId,colorId,quantity);
            inventoryRepository.delete(inventoryFound.get());
            Inventory newInventory = new Inventory(productId,sizeId,colorId,newInventoryQuantity);
            inventoryRepository.save(newInventory);

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

            orderDetailsRepository.save(orderDetails);

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
     *
     * @param createOrderRequestModel
     * Registered user submitting orders
     *
     */
    @PreAuthorize("hasAuthority('USER')")
    @PostMapping(path = "/order")
    public OrderIdResponseModel addNewOrder(HttpServletRequest request, @RequestHeader(value = "Authorization",required = true) String headerVal, @Valid @RequestBody CreateOrderRequestModel createOrderRequestModel ) {
        /***
         * decode TOKEN's and validate against the details
         */
        String token = headerVal.substring(headerVal.indexOf(" "), headerVal.length());
            String userId = jwtProvider.getUserId(token);

            if (!securityUserService.isValidToken(token)) {
                throw new RuntimeException("TOKEN IS INVALID ");
            }
            Optional<SecurityUser> user = securityUserRepository.findByPhoneNumber(jwtProvider.getPhone(token));

            if (!user.isPresent()) {
                throw new RuntimeException("USER NOT FOUND, PLEASE CHECK YOUR INFORMATION BEFORE PROCEEDING TO ORDER");
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
                ObjectId sizeObjectId = new ObjectId(sizeId);
                Size sizeFound = sizeRepository.findBySizeId(sizeObjectId);
                String sizeName = sizeFound.getSizeName();

                /**
                 * Retrieving the unit price for this product
                 * */
                Optional<ProductSize> productSizeFound = productSizeRepository.findByProductIdAndSizeName(productId,sizeName);
                double unitPrice = productSizeFound.get().getPrice();

                /**
                 * Calculate the price for calculatedPrice field
                 * */
                double calculatedPrice = quantity * unitPrice;

                /**
                 * Checking each product's quantity against one in Inventory
                 * Updating remaining quantity
                 * */
                Optional<Inventory> inventoryFound =  inventoryRepository.findByProductIdAndProductSizeIdAndColorId(productId,sizeId,colorId);
                int inventoryQuantity = inventoryFound.get().getQuantity();

                if( quantity > inventoryQuantity ){
                    throw new RuntimeException("The product quantity exceeds inventory's");
                }

                int newInventoryQuantity = inventoryQuantity - quantity;

                /**
                 * Update new Inventory's quantity
                 * */
                inventoryRepository.delete(inventoryFound.get());
                Inventory newInventory = new Inventory(productId,sizeId,colorId,newInventoryQuantity);
                inventoryRepository.save(newInventory);

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

                orderDetailsRepository.save(orderDetails);

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
        LOGGER.info("this api is being called");
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
        String token = headerVal.substring(headerVal.indexOf(" "), headerVal.length());

        if (!securityUserService.isValidToken(token)) {
            throw new InvalidTokenException("TOKEN IS INVALID");
        }

        String securityUserId = jwtProvider.getUserId(token);

        if(securityUserId == null){
            throw new TokenUserIdNotFoundException(String.format("USER WITH {userId} NOT FOUND",securityUserId));
        }

        Optional<SecurityUser> user = securityUserRepository.findByPhoneNumber(jwtProvider.getPhone(token));

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
