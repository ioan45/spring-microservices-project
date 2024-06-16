package com.example.usermicroservice.controllers;

import com.example.usermicroservice.dto.*;
import com.example.usermicroservice.services.UserService;
import com.example.usermicroservice.swagger.UserControllerSwagger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
public class UserController implements UserControllerSwagger {

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
        int userId = userService.signUpUser(userData);

        // Constructing the response by adding a link to information retrieval for the newly created user.
        EntityModel<SignUpUserResponse> response = EntityModel.of(
                new SignUpUserResponse("User has been successfully registered!"),
                linkTo(methodOn(UserController.class).signUpUser(userData)).withSelfRel(),
                linkTo(methodOn(UserController.class).getUserInfo(userId)).withRel("getUserInfo")
        );
        return ResponseEntity.ok().body(response);
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
    public ResponseEntity<?> getUserInfo(
            @PathVariable("id") int userId
    ) {
        logger.info("Processing user data retrieval request!");
        UserInfoResponse userInfo = userService.getUserData(userId);
        return ResponseEntity.ok().body(userInfo);
    }
}
