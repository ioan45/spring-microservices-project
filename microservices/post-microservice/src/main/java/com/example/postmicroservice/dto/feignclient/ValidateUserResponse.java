package com.example.postmicroservice.dto.feignclient;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ValidateUserResponse {
    private boolean isValid;
    private int userId;
}
