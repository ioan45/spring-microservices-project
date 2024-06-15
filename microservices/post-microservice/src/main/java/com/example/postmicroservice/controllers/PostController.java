package com.example.postmicroservice.controllers;

import com.example.postmicroservice.dto.*;
import com.example.postmicroservice.services.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PostController {
    private final Logger logger;
    private final PostService postService;

    public PostController(PostService postService) {
        this.logger = LoggerFactory.getLogger(PostController.class);;
        this.postService = postService;
    }

    @PostMapping("/new")
    public ResponseEntity<?> newPost(
            @RequestBody NewPostRequest req
    ) {
        logger.info("Processing post creation request! Data: " + req.toString());
        postService.createPost(req);
        return ResponseEntity.ok().body("The post was created successfully!");
    }

    @PostMapping("/new/comment")
    public ResponseEntity<?> newComment(
            @RequestBody NewCommentRequest req
    ) {
        logger.info("Processing comment creation request! Data: " + req.toString());
        postService.createComment(req);
        return ResponseEntity.ok().body("The comment was created successfully!");
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deletePost(
            @RequestBody DeletePostRequest req
    ) {
        logger.info("Processing post removal request! Data: " + req.toString());
        postService.deletePost(req);
        return ResponseEntity.ok().body("The post was deleted successfully!");
    }

    @PostMapping("/delete/comment")
    public ResponseEntity<?> deleteComment(
            @RequestBody DeleteCommentRequest req
    ) {
        logger.info("Processing comment removal request! Data: " + req.toString());
        postService.deleteComment(req);
        return ResponseEntity.ok().body("The comment was deleted successfully!");
    }

    @GetMapping("/get/all")
    public ResponseEntity<?> getAllPosts() {
        logger.info("Processing all posts data retrieval request!");
        List<PostInfoResponse> posts = postService.getAllPosts();
        return ResponseEntity.ok().body(posts);
    }

    @GetMapping("/{id}/get/comments")
    public ResponseEntity<?> getCommentsForPost(
            @PathVariable("id") int postId
    ) {
        logger.info("Processing all post comments retrieval request!");
        List<CommentInfoResponse> comments = postService.getCommentsForPost(postId);
        return ResponseEntity.ok().body(comments);
    }
}
