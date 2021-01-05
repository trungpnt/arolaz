package com.ecommerce.arolaz.OrderDetails.Service;

import com.ecommerce.arolaz.OrderDetails.Model.OrderDetails;
import com.ecommerce.arolaz.OrderDetails.Repository.OrderDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailsServiceImpl implements OrderDetailsService {
    @Autowired
    private OrderDetailsRepository orderDetailsRepository;
    @Override
    public OrderDetails addOrderDetails(OrderDetails orderDetails) {
        return orderDetailsRepository.save(orderDetails);
    }
}
