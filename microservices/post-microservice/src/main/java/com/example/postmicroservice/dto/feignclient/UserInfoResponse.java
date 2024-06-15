package com.example.postmicroservice.dto.feignclient;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserInfoResponse {
    private int userId;
    private String username;
    private String email;
}
