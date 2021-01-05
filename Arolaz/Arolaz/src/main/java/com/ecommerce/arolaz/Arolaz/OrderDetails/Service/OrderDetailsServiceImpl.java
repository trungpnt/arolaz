package com.ecommerce.arolaz.Arolaz.OrderDetails.Service;

import com.ecommerce.arolaz.Arolaz.OrderDetails.Model.OrderDetails;
import com.ecommerce.arolaz.Arolaz.OrderDetails.Repository.OrderDetailsRepository;
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
