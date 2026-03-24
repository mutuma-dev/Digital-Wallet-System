package com.wallet.userservice.mapper;

import com.wallet.userservice.dto.UserCreateRequest;
import com.wallet.userservice.dto.UserResponse;
import com.wallet.userservice.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    // DTO -> Entity
    public User toUserEntity(UserCreateRequest request) {

        if (request == null) {
            return null;
        }

        User user = new User();

        // Map fields
        user.setFirstName(request.getFirstName());
        user.setSecondName(request.getSecondName());


        return user;
    }

    // Entity -> DTO
    public UserResponse toUserResponse(User user) {

        if (user == null) {
            return null;
        }

        UserResponse response = new UserResponse();

        response.setUserId(user.getUserId());
        response.setFirstName(user.getFirstName());
        response.setSecondName(user.getSecondName());

        return response;
    }
}