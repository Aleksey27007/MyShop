package com.shop.myshop.service.user;

import com.shop.myshop.dto.UserDto;
import com.shop.myshop.model.User;

import java.util.List;

public interface UserService {
    User getUserById(Long userId);
    User createUser(UserDto userDto);
    User updateUser(UserDto userDto, Long userId);
    void deleteUser(Long userId);

    List<User> getUsers();
}
