package com.shop.myshop.mapper;

import com.shop.myshop.dto.ProductDto;
import com.shop.myshop.dto.UserDto;
import com.shop.myshop.model.Product;
import com.shop.myshop.model.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final ModelMapper modelMapper;


    public UserDto convertToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    public List<UserDto> getConvertedUsers(List<User> users) {
        return users.stream().map(this::convertToDto).toList();
    }
}
