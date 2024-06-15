package com.example.postmicroservice.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class DeletePostRequest {
    private String username;
    private String password;
    private int postId;
}
