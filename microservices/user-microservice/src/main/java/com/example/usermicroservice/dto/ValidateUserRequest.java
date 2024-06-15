package com.example.usermicroservice.dto;

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
