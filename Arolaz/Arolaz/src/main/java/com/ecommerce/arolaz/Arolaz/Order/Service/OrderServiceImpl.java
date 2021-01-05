package com.ecommerce.arolaz.Arolaz.Order.Service;

import com.ecommerce.arolaz.Arolaz.Order.Model.Order;
import com.ecommerce.arolaz.Arolaz.Order.Repository.OrderRepository;
import com.ecommerce.arolaz.Arolaz.SecurityUser.Service.SecurityUserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private SecurityUserService securityUserService;

    @Override
    public Page<Order> findBySecurityUserId(String securityUserId, Pageable pageable) {
        return orderRepository.findBySecurityUserId(securityUserId, pageable);
    }

    @Override
    public Order addOrder(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Page<Order> findAllOrders(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    @Override
    public Optional<Order> findByOrderId(ObjectId orderId) {
        return orderRepository.findById(orderId);
    }
}
