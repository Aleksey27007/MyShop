package com.shop.myshop.controller;

import com.shop.myshop.dto.OrderDto;
import com.shop.myshop.exceptions.ResourceNotFoundException;
import com.shop.myshop.mapper.OrderMapper;
import com.shop.myshop.model.Order;
import com.shop.myshop.response.ApiResponse;
import com.shop.myshop.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/orders")
public class OrderController {
    private final OrderService orderService;
    private final OrderMapper orderMapper;

    @PostMapping("/{userId}") // http://localhost:8080/api/v1/orders/1
    public ResponseEntity<ApiResponse> createOrder(@PathVariable Long userId) {
        try {
            Order order =  orderService.placeOrder(userId);
            OrderDto orderDto = orderMapper.convertToDto(order);
            return ResponseEntity.ok(new ApiResponse("Item Order Success!", orderDto));
        } catch (Exception e) {
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Error Occured!", e.getMessage()));
        }
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<ApiResponse> getOrderById(@PathVariable Long orderId) {
        try {
            OrderDto order = orderService.getOrder(orderId);
            return ResponseEntity.ok(new ApiResponse("Item Order Success!", order));
        } catch (ResourceNotFoundException e) {
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Not found!", e.getMessage()));
        }
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<ApiResponse> getUserOrders(@PathVariable Long userId) {
        try {
            List<OrderDto> order = orderService.getUserOrders(userId);
            return ResponseEntity.ok(new ApiResponse("Item Order Success!", order));
        } catch (ResourceNotFoundException e) {
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Not found!", e.getMessage()));
        }
    }
}
