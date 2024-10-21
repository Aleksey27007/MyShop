package com.shop.myshop.service.user;

import com.shop.myshop.dto.UserDto;
import com.shop.myshop.exceptions.AlreadyExistsException;
import com.shop.myshop.exceptions.ResourceNotFoundException;
import com.shop.myshop.model.User;
import com.shop.myshop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found!"));
    }

    @Override
    public User createUser(UserDto userDto) {
        return Optional.of(userDto)
                .filter(user -> !userRepository.existsByEmail(userDto.getEmail()))
                .map(req -> {
                    User user = new User();
                    user.setEmail(userDto.getEmail());
                    user.setPassword(userDto.getPassword());
                    user.setFirstName(userDto.getFirstName());
                    user.setLastName(userDto.getLastName());
                    return userRepository.save(user);
                }).orElseThrow(() -> new AlreadyExistsException(userDto.getEmail() + " already exists!"));
    }

    @Override
    public User updateUser(UserDto userDto, Long userId) {
        return userRepository.findById(userId).map(existingUser -> {
            existingUser.setFirstName(userDto.getFirstName());
            existingUser.setLastName(userDto.getLastName());
            return userRepository.save(existingUser);
        }).orElseThrow(() -> new ResourceNotFoundException("User not found!"));

    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.findById(userId).ifPresentOrElse(userRepository::delete, () -> {
            throw new ResourceNotFoundException("User not found!");
        });
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }
}
