package com.example.usermicroservice.dto;

import com.example.usermicroservice.entities.User;
import org.springframework.stereotype.Component;

@Component
public class UserDtoMapper {
    public User fromSignUpRequest(SignUpUserRequest dto) {
        User newUser = new User();
        newUser.setUsername(dto.getUsername());
        newUser.setPassword(dto.getPassword());
        newUser.setEmail(dto.getEmail());
        return newUser;
    }

    public User fromValidateRequest(ValidateUserRequest dto) {
        User newUser = new User();
        newUser.setUsername(dto.getUsername());
        newUser.setPassword(dto.getPassword());
        return newUser;
    }

    public UserInfoResponse toInfoResponse(User userEntity) {
        UserInfoResponse dto = new UserInfoResponse();
        dto.setUserId(userEntity.getUserId());
        dto.setUsername(userEntity.getUsername());
        dto.setEmail(userEntity.getEmail());
        return dto;
    }
}
