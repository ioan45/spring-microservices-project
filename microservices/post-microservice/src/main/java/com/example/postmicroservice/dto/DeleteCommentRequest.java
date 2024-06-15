package com.example.postmicroservice.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class DeleteCommentRequest {
    private String username;
    private String password;
    private int commentId;
}
