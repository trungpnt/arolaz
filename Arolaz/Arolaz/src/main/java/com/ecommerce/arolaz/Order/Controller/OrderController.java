package com.ecommerce.arolaz.Order.Controller;

import com.ecommerce.arolaz.Order.Model.Order;
import com.ecommerce.arolaz.Order.RequestResponseModels.CreateOrderRequestModel;
import com.ecommerce.arolaz.Order.RequestResponseModels.OrderIdResponseModel;
import com.ecommerce.arolaz.Order.RequestResponseModels.OrderResponseModel;
import com.ecommerce.arolaz.Order.RequestResponseModels.PagedOrderResponseModel;
import com.ecommerce.arolaz.Order.Service.OrderService;
import com.ecommerce.arolaz.OrderDetails.Model.OrderDetails;
import com.ecommerce.arolaz.OrderDetails.RequestResponseModels.CreateOrderDetailsElementModel;
import com.ecommerce.arolaz.OrderDetails.Service.OrderDetailsService;
import com.ecommerce.arolaz.Product.Model.Product;
import com.ecommerce.arolaz.Product.Repository.ProductRepository;
import com.ecommerce.arolaz.Product.Service.ProductService;
import com.ecommerce.arolaz.ProductSize.Model.ProductSize;
import com.ecommerce.arolaz.ProductSize.Repository.ProductSizeRepository;
import com.ecommerce.arolaz.Security.JwtProvider;
import com.ecommerce.arolaz.SecurityUser.Model.SecurityUser;
import com.ecommerce.arolaz.SecurityUser.Service.SecurityUserService;
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
    private OrderDetailsService orderDetailsRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

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
    private JwtProvider jwtProvider;

    @Autowired
    private ModelMapper modelMapper;

    private Order order = new Order();

    private OrderDetails orderDetails = new OrderDetails();

    private ArrayList<OrderDetails> orderDetailsArrayList = new ArrayList<>();

    private OrderDetails toOrderDetailsDomain (String orderId){
        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setOrderId(orderId);
        return orderDetails;
    }

    /**
     *
     * @param createOrderRequestModel
     * Get List of ordered products from createOrderRequestModel to insert into the Order domain
     *
     */
    @PreAuthorize("hasAuthority('USER')")
    @PostMapping(path = "/order")
    public OrderIdResponseModel createOrder(HttpServletRequest request, @RequestHeader(value = "Authorization") String headerVal, @Valid @RequestBody CreateOrderRequestModel createOrderRequestModel) {
            String token = headerVal.substring(headerVal.indexOf(" "), headerVal.length());
            String userId = jwtProvider.getUserId(token);

            if (!securityUserService.isValidToken(token)) {
                throw new RuntimeException("TOKEN IS INVALID ");
            }
            Optional<SecurityUser> user = securityUserRepository.findByPhone(jwtProvider.getPhone(token));

            if (!user.isPresent()) {
                throw new RuntimeException("USER NOT FOUND, PLEASE CHECK THE INFO BEFORE PROCEEDING ORDERING");
            }

            Order order = toOrderDomain(createOrderRequestModel, userId);
            Order persistOrder = orderService.addOrder(order);

            String getOrderId = persistOrder.getOrderId().toString();

            //PERSISTING ORDER DETAILS
            for ( int i = 0; i < orderDetailsArrayList.size(); i++){
                orderDetailsArrayList.get(i).setOrderId(getOrderId);
                orderDetailsRepository.addOrderDetails(orderDetailsArrayList.get(i));
            }


            return new OrderIdResponseModel(order.getOrderId().toString());
    }

    private Order toOrderDomain(CreateOrderRequestModel createOrderRequestModel, String userId) {
        order.setAddress(createOrderRequestModel.getAddress());
        order.setNote(createOrderRequestModel.getNote());
        order.setSecurityUserId(userId);
        order.setTotalQuantity(createOrderRequestModel.getCreateOrderDetails().size());

        Double totalPrice = new Double(0);
        //Should move the line below upstairs
        for (int i = 0, n = createOrderRequestModel.getCreateOrderDetails().size(); i < n; i++) {
            //Initializes each cursor record of type elementModel
            CreateOrderDetailsElementModel elementModel = createOrderRequestModel.getCreateOrderDetails().get(i);

            //Get product id
            String persistProductId = elementModel.getProductId();
            ObjectId proId = new ObjectId(persistProductId);
            Optional<Product> product = productRepository.findById(proId);

            //Get product price
            //Double productPrice = product.get().g();

            //Get product priceByType name
            String productSizeId = elementModel.getProductSizeId();
            ObjectId sizeId = new ObjectId(productSizeId);
            Optional<ProductSize> productSize = productSizeRepository.findById(sizeId);
            Double priceBySize = productSize.get().getPrice();

            orderDetails.setProductId(persistProductId);
            orderDetails.setProductSizeId(productSizeId);
            //LATER
            //orderDetails.setUnitPrice(productPrice);

            orderDetailsArrayList.add(orderDetails);

        }
        order.setProducts(orderDetailsArrayList);
        order.setTotalPrice(totalPrice);
        return order;
    }
//    private void persistOrderDetails(CreateOrderRequestModel createOrderRequestModel, int productIndex, OrderDetails orderDetails ){
//        orderDetails.setProductId(createOrderRequestModel.getProducts().get(productIndex).getId());
//        orderDetails.setAmount(createOrderRequestModel.getProducts().get(productIndex).getAmount());
//        orderDetails.setSizeId(createOrderRequestModel.getProducts().get(productIndex).getSizeId());
//
//        ObjectId productId = new ObjectId(createOrderRequestModel.getProducts().get(productIndex).getId());
//        Optional<Product> found = productRepository.findById(productId);
//        orderDetails.setUnitPrice(found.get().getPrice());
//        orderDetailsRepository.addOrderDetails(orderDetails);
//    }

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
        orderResponseModel.setProducts(order.get().getProducts());
        return orderResponseModel;
    }

    @GetMapping(path = "/order")
    @PreAuthorize("hasAuthority('USER')")
    public CustomizedPagingResponseModel<PagedOrderResponseModel> getOrdersByUserToken(
            @RequestParam("page") Integer page,
            @RequestParam("rows") Integer rows,
            Pageable pageable,
            HttpServletRequest request, @RequestHeader(value = "Authorization") String headerVal) {

        Page<Order> orderPage;
        String token = headerVal.substring(headerVal.indexOf(" "), headerVal.length());

        if (!securityUserService.isValidToken(token)) {
            LOGGER.info("Invalid token");
        }

        String securityUserId = jwtProvider.getUserId(token);

        if(securityUserId == null){
            throw new RuntimeException("USER NOT FOUND !");
        }
        Optional<SecurityUser> user = securityUserRepository.findByPhone(jwtProvider.getPhone(token));

        if (!user.isPresent()) {
            throw new RuntimeException("USER NOT FOUND !");
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
        return new OrderResponseModel(order.getOrderId().toString(),order.getTotalPrice(),order.getTotalQuantity(),order.getAddress(),order.getNote(),order.getProducts());
    }

    private PagedOrderResponseModel toDtoPage(Order order){
        return new PagedOrderResponseModel(order.getOrderId().toString(),order.getTotalPrice(),order.getTotalQuantity(),order.getAddress(),order.getNote());
    }
}
