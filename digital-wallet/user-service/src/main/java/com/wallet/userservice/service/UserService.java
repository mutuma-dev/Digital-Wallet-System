package com.wallet.userservice.service;

import com.wallet.userservice.constants.ExceptionConstants;
import com.wallet.userservice.dto.UserCreateRequest;
import com.wallet.userservice.dto.UserResponse;
import com.wallet.userservice.entity.User;
import com.wallet.userservice.mapper.UserMapper;
import com.wallet.userservice.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    // CREATE USER
    public UserResponse createUser(UserCreateRequest request) {

        // DTO -> Entity
        User user = userMapper.toUserEntity(request);

        // Save user
        User savedUser = userRepository.save(user);

        // Entity -> DTO
        return userMapper.toUserResponse(savedUser);
    }

    // GET USER BY ID
    public UserResponse getUserById(UUID id) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(ExceptionConstants.USER_NOT_FOUND + id)
                );

        return userMapper.toUserResponse(user);
    }

    // GET ALL USERS
    public List<UserResponse> getAllUsers() {

        return userRepository.findAll()
                .stream()
                .map(userMapper::toUserResponse)
                .toList();
    }
}