package com.example.postmicroservice.controllers;

import com.example.postmicroservice.dto.*;
import com.example.postmicroservice.services.PostService;
import com.example.postmicroservice.swagger.PostControllerSwagger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/post")
public class PostController implements PostControllerSwagger {
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
        int newPostId = postService.createPost(req);

        // Construct the response which includes HATEOAS links to self and
        // retrieving all the posts created, including this new one.
        EntityModel<NewPostResponse> response = EntityModel.of(
                new NewPostResponse("The post was created successfully!"),
                linkTo(methodOn(PostController.class).newPost(req)).withSelfRel(),
                linkTo(methodOn(PostController.class).getAllPosts()).withRel("getPosts")
        );
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/new/comment")
    public ResponseEntity<?> newComment(
            @RequestBody NewCommentRequest req
    ) {
        logger.info("Processing comment creation request! Data: " + req.toString());
        int postId = postService.createComment(req);

        // Construct the response which includes HATEOAS links to self and
        // retrieving all the comments made on the specified post, including this new one.
        EntityModel<NewCommentResponse> response = EntityModel.of(
                new NewCommentResponse("The comment was created successfully!"),
                linkTo(methodOn(PostController.class).newComment(req)).withSelfRel(),
                linkTo(methodOn(PostController.class).getCommentsForPost(postId)).withRel("postComments")
        );
        return ResponseEntity.ok().body(response);
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

        // Constructing the response by adding to each post a link to retrieve its comments.
        List<EntityModel<PostInfoResponse>> response = posts.stream().map(post -> EntityModel.of(
                post,
                linkTo(methodOn(PostController.class).getCommentsForPost(post.getPostId())).withRel("comments")
        )).toList();
        return ResponseEntity.ok().body(response);
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
