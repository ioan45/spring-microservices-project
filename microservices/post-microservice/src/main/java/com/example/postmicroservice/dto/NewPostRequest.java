package com.example.postmicroservice.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class NewPostRequest {
    private String username;
    private String password;
    private String content;
}
