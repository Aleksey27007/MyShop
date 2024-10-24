package com.shop.myshop.mapper;

import com.shop.myshop.dto.OrderDto;
import com.shop.myshop.model.Order;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderMapper {

    private final ModelMapper modelMapper;

    public OrderDto convertToDto(Order order) {
        return modelMapper.map(order, OrderDto.class);
    }

}
