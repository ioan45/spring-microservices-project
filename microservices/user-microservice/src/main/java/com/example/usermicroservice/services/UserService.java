package com.example.usermicroservice.services;

import com.example.usermicroservice.dto.*;
import com.example.usermicroservice.entities.User;
import com.example.usermicroservice.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserDtoMapper userDtoMapper;

    public UserService(UserRepository userRepository, UserDtoMapper userDtoMapper) {
        this.userRepository = userRepository;
        this.userDtoMapper = userDtoMapper;
    }

    public int signUpUser(SignUpUserRequest userDto) {
        User user = userDtoMapper.fromSignUpRequest(userDto);
        boolean userExists = userRepository.existsByUsername(user.getUsername());
        if (userExists)
            throw new RuntimeException("Sign up failed! User already exists.");
        BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
        user.setPassword(bc.encode(user.getPassword()));
        return userRepository.save(user).getUserId();
    }

    public ValidateUserResponse isValidUser(ValidateUserRequest userDto) {
        User dbUser = userRepository.findByUsername(userDto.getUsername()).orElse(null);
        if (dbUser != null) {
            BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
            boolean validPassword = bc.matches(userDto.getPassword(), dbUser.getPassword());
            if (validPassword)
                return new ValidateUserResponse(true, dbUser.getUserId());
        }
        return new ValidateUserResponse(false, 0);
    }

    public List<UserInfoResponse> getAllUsersInfo() {
        List<User> users = userRepository.findAll();
        return users.stream().map(userDtoMapper::toInfoResponse).toList();
    }

    public UserInfoResponse getUserData(int userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null)
            throw new RuntimeException("User info retrieval failed! There is no user with the specified ID.");
        return userDtoMapper.toInfoResponse(user);
    }
}
