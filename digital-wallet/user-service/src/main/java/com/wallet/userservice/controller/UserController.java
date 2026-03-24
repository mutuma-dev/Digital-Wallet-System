package com.wallet.userservice.controller;

import com.wallet.userservice.constants.ApiPaths;
import com.wallet.userservice.dto.UserCreateRequest;
import com.wallet.userservice.dto.UserResponse;
import com.wallet.userservice.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(ApiPaths.API_VERSION_URL + ApiPaths.USER_BASE_URL)
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    // CREATE USER

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody UserCreateRequest request) {

        UserResponse response = userService.createUser(request);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    // GET USER BY ID

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable UUID id) {

        UserResponse response = userService.getUserById(id);

        return ResponseEntity.ok(response);
    }

    // GET ALL USERS

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {

        List<UserResponse> users = userService.getAllUsers();

        return ResponseEntity.ok(users);
    }
}