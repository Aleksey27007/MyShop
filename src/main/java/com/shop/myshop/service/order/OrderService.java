package com.shop.myshop.service.order;

import com.shop.myshop.dto.OrderDto;
import com.shop.myshop.model.Order;

import java.util.List;

public interface OrderService {
    Order placeOrder(Long userId);
    OrderDto getOrder(Long orderId);
    List<OrderDto> getUserOrders(Long userId);
}
