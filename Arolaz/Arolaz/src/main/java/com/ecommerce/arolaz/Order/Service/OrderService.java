package com.ecommerce.arolaz.Order.Service;

import com.ecommerce.arolaz.Order.Model.Order;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    Page<Order> findBySecurityUserId(String securityUserId, Pageable pageable);
    Order addOrder(Order order);
    Page<Order> findAllOrders(Pageable pageable);
    Optional<Order> findByOrderId(ObjectId OrderId);
    public Page<Order> findByUnregisteredUserId(String unregisteredUserId,Pageable pageable);
}
