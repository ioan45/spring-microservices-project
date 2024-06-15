package com.example.postmicroservice.dto.feignclient;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ValidateUserRequest {
    private String username;
    private String password;
}
