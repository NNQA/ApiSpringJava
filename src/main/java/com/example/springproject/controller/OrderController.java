package com.example.springproject.controller;


import com.example.springproject.payload.Request.OrderItemRequest;
import com.example.springproject.payload.Response.MessageResponse;
import com.example.springproject.payload.Response.OrderDetails;
import com.example.springproject.security.JWT.JWTTokenProvider;
import com.example.springproject.security.service.Iml.OrderService;
import org.cache2k.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        return ResponseEntity.ok("ok");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(e.getMessage()));
        }
    }
    @GetMapping("/getOrder")
    public ResponseEntity<?> getOder(HttpServletRequest request) {
        try {
            String token = jwtUils.getJwtFromCookies(request);
            Long id = jwtUils.getUserId(token);
            OrderDetails orderDetails = orderService.getOrderDetails(id);
            return ResponseEntity.ok(orderDetails);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(e.getMessage()));
        }
    }
    @PutMapping("/udapteOrderitem/{orderId}")
    public ResponseEntity<?> updateOrderItem(@PathVariable Long orderId,@RequestBody OrderItemRequest orderItemRequest, HttpServletRequest request) {
        try {
            String token = jwtUils.getJwtFromCookies(request);
            Long id = jwtUils.getUserId(token);
            orderService.updateOrderItem(orderId,id, orderItemRequest);
            return ResponseEntity.ok("ok");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(e.getMessage()));
        }
    }
    @PutMapping("/checkout/{orderId}")
    public ResponseEntity<?> checkOutItem(@PathVariable Long orderId, HttpServletRequest request) {
        try {
            String token = jwtUils.getJwtFromCookies(request);
            Long id = jwtUils.getUserId(token);
            orderService.checkout(orderId,id);
            return ResponseEntity.ok("ok");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(e.getMessage()));
        }
    }
    @DeleteMapping("/deleteItem/{orderId}")
    public ResponseEntity<?> deleteItem(@PathVariable Long orderId,@RequestParam Long itemId, HttpServletRequest request) {
        try {
            String token = jwtUils.getJwtFromCookies(request);
            Long id = jwtUils.getUserId(token);
            orderService.deleteItem(orderId, itemId, id);

            return ResponseEntity.ok("Delete Successfully");
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(e.getMessage()));
        }
    }
}
