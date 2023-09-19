package com.example.springproject.controller;


import com.example.springproject.payload.Response.MessageResponse;
import com.example.springproject.security.JWT.JWTTokenProvider;
import com.example.springproject.security.service.Iml.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/order")
public class OrderController {


    private final JWTTokenProvider jwtUils;
    private final OrderService orderService;

    public OrderController(JWTTokenProvider jwtUils, OrderService orderService) {
        this.jwtUils = jwtUils;
        this.orderService = orderService;
    }

    @PostMapping("/addOrder/{idProduct}")
    public ResponseEntity<?> addOrder(@PathVariable Long idProduct, HttpServletRequest request) {
        try {
        String token = jwtUils.getJwtFromCookies(request);
        Long id = jwtUils.getUserId(token);
        orderService.addOrder(idProduct, id);
        return ResponseEntity.ok("asdsad");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(e.getMessage()));
        }
    }
}
