package com.example.postmicroservice.dto;

import com.example.postmicroservice.entities.Comment;
import com.example.postmicroservice.entities.Post;
import org.springframework.stereotype.Component;

@Component
public class PostDtoMapper {
    public Post fromNewPostRequest(NewPostRequest req) {
        Post post = new Post();
        post.setContent(req.getContent());
        return post;
    }

    public Comment fromNewCommentRequest(NewCommentRequest req) {
        Comment comment = new Comment();
        comment.setContent(req.getContent());
        return comment;
    }

    public PostInfoResponse toPostInfoResponse(Post post) {
        PostInfoResponse info = new PostInfoResponse();
        info.setPostId(post.getPostId());
        info.setTimestamp(post.getTimestamp());
        info.setContent(post.getContent());
        return info;
    }

    public CommentInfoResponse toCommentInfoResponse(Comment comment) {
        CommentInfoResponse info = new CommentInfoResponse();
        info.setCommentId(comment.getCommentId());
        info.setTimestamp(comment.getTimestamp());
        info.setContent(comment.getContent());
        info.setPostId(comment.getPost().getPostId());
        return info;
    }
}
