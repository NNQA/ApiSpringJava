package com.example.springproject.payload.Response;

import com.example.springproject.DTO.OrderItemDTO;
import com.example.springproject.models.OrderItem;

import java.util.List;

public class OrderDetails {

    private String userName;
    private List<OrderItemDTO> orderItemDTOS;

    public OrderDetails(String userName, List<OrderItemDTO> orderItemDTOS) {
        this.userName = userName;
        this.orderItemDTOS = orderItemDTOS;
    }

    public List<OrderItemDTO> getOrderItemDTOS() {
        return orderItemDTOS;
    }

    public void setOrderItemDTOS(List<OrderItemDTO> orderItemDTOS) {
        this.orderItemDTOS = orderItemDTOS;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
