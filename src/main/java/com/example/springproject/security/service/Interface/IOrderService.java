package com.example.springproject.security.service.Interface;

import com.example.springproject.payload.Request.OrderItemRequest;
import com.example.springproject.payload.Response.OrderDetails;

import java.util.List;

public interface IOrderService {
    void addOrder(Long productId, Long userId);
    OrderDetails getOrderDetails(Long userId);
    void updateOrderItem(Long orderId, Long userId, OrderItemRequest orderItemRequest);
}
