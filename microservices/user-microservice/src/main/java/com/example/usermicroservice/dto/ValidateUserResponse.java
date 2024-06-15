package com.example.usermicroservice.dto;

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
