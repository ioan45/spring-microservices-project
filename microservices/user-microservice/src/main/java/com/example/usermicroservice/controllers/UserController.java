package com.example.usermicroservice.controllers;

import com.example.usermicroservice.dto.SignUpUserRequest;
import com.example.usermicroservice.dto.UserInfoResponse;
import com.example.usermicroservice.dto.ValidateUserRequest;
import com.example.usermicroservice.dto.ValidateUserResponse;
import com.example.usermicroservice.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private final Logger logger;
    private final UserService userService;

    public UserController(UserService userService) {
        this.logger = LoggerFactory.getLogger(UserController.class);
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUpUser(
            @RequestBody SignUpUserRequest userData
    ) {
        logger.info("Processing user sign up request! Data: " + userData.toString());
        userService.signUpUser(userData);
        return ResponseEntity.ok().body("User has been successfully registered!");
    }

    @GetMapping("/isvalid")
    public ResponseEntity<ValidateUserResponse> isUserValid(
            @RequestBody ValidateUserRequest userData
    ) {
        logger.info("Processing user validation request! Data: " + userData.toString());
        ValidateUserResponse response = userService.isValidUser(userData);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/get/all")
    public ResponseEntity<List<UserInfoResponse>> getAllUsers() {
        logger.info("Processing all users data retrieval request!");
        List<UserInfoResponse> users = userService.getAllUsersInfo();
        return ResponseEntity.ok().body(users);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<UserInfoResponse> getUserInfo(
            @PathVariable("id") int userId
    ) {
        logger.info("Processing user data retrieval request!");
        UserInfoResponse userInfo = userService.getUserData(userId);
        return ResponseEntity.ok().body(userInfo);
    }
}
