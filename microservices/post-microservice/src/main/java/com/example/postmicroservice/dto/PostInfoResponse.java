package com.example.postmicroservice.dto;

import lombok.*;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PostInfoResponse {
    private int postId;
    private String username;
    private Timestamp timestamp;
    private String content;
}
