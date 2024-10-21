package com.shop.myshop.controller;

import com.shop.myshop.dto.ProductDto;
import com.shop.myshop.dto.UserDto;
import com.shop.myshop.exceptions.AlreadyExistsException;
import com.shop.myshop.exceptions.ResourceNotFoundException;
import com.shop.myshop.mapper.UserMapper;
import com.shop.myshop.model.Product;
import com.shop.myshop.model.User;
import com.shop.myshop.response.ApiResponse;
import com.shop.myshop.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/users")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("") // http://localhost:8080/api/v1/users
    public ResponseEntity<ApiResponse> getAllUsers() {
        List<User> users = userService.getUsers();
        List<UserDto> convertedUsers = userMapper.getConvertedUsers(users);
        return  ResponseEntity.ok(new ApiResponse("success", convertedUsers));
    }

    @GetMapping("/by-id/{userId}") // http://localhost:8080/api/v1/users/by-id/1
    public ResponseEntity<ApiResponse> getUserById(@PathVariable Long userId) {
        try {
            User user = userService.getUserById(userId);
            UserDto userDto = userMapper.convertToDto(user);
            return ResponseEntity.ok(new ApiResponse("Success", userDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping("") // http://localhost:8080/api/v1/users
    public ResponseEntity<ApiResponse> createUser(@RequestBody UserDto userDtoReq) {
        try {
            User user = userService.createUser(userDtoReq);
            UserDto userDto = userMapper.convertToDto(user);
            return ResponseEntity.ok(new ApiResponse("Create User Success!", userDto));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(), null));
        }
    }
    @PutMapping("/update/{userId}") // http://localhost:8080/api/v1/users/update/1
    public ResponseEntity<ApiResponse> updateUser(@RequestBody UserDto userDtoReq, @PathVariable Long userId) {
        try {
            User user = userService.updateUser(userDtoReq, userId);
            UserDto userDto = userMapper.convertToDto(user);
            return ResponseEntity.ok(new ApiResponse("Update User Success!", userDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
    @DeleteMapping("/delete/{userId}") // http://localhost:8080/api/v1/users/delete/1
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long userId) {
        try {
            userService.deleteUser(userId);
            return ResponseEntity.ok(new ApiResponse("Delete User Success!", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
