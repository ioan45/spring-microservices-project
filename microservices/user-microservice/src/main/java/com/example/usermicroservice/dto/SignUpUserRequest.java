package com.example.usermicroservice.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SignUpUserRequest {
    private String username;
    private String email;
    private String password;
}
