package com.example.postmicroservice.services;

import com.example.postmicroservice.dto.*;
import com.example.postmicroservice.dto.feignclient.UserInfoResponse;
import com.example.postmicroservice.dto.feignclient.ValidateUserRequest;
import com.example.postmicroservice.dto.feignclient.ValidateUserResponse;
import com.example.postmicroservice.entities.Comment;
import com.example.postmicroservice.entities.Post;
import com.example.postmicroservice.feignclient.UserClient;
import com.example.postmicroservice.repositories.CommentRepository;
import com.example.postmicroservice.repositories.PostRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final PostDtoMapper postDtoMapper;
    private final UserClient userClient;

    public PostService(PostRepository postRepository, CommentRepository commentRepository, PostDtoMapper postDtoMapper, UserClient userClient) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.postDtoMapper = postDtoMapper;
        this.userClient = userClient;
    }

    public void createPost(NewPostRequest postDto) {
        ValidateUserResponse validation = this.validateUser(postDto.getUsername(), postDto.getPassword());
        if (!validation.isValid())
            throw new RuntimeException("Post creation failed! There is no user with the given credentials.");

        Post newPost = postDtoMapper.fromNewPostRequest(postDto);
        newPost.setTimestamp(new Timestamp(System.currentTimeMillis()));
        newPost.setUserId(validation.getUserId());
        postRepository.save(newPost);
    }

    public void createComment(NewCommentRequest commentDto) {
        Post post = postRepository.findById(commentDto.getPostId()).orElse(null);
        if (post == null)
            throw new RuntimeException("Comment creation failed! The specified post doesn't exist.");

        ValidateUserResponse validation = this.validateUser(commentDto.getUsername(), commentDto.getPassword());
        if (!validation.isValid())
            throw new RuntimeException("Comment creation failed! There is no user with the given credentials.");

        Comment newComment = postDtoMapper.fromNewCommentRequest(commentDto);
        newComment.setTimestamp(new Timestamp(System.currentTimeMillis()));
        newComment.setPost(post);
        newComment.setUserId(validation.getUserId());
        commentRepository.save(newComment);
    }

    @Transactional
    public void deletePost(DeletePostRequest postDto) {
        Post post = postRepository.findById(postDto.getPostId()).orElse(null);
        if (post == null)
            throw new RuntimeException("Post removal failed! The specified post doesn't exist.");

        ValidateUserResponse validation = this.validateUser(postDto.getUsername(), postDto.getPassword());
        if (!validation.isValid() || validation.getUserId() != post.getUserId())
            throw new RuntimeException("Post removal failed! You are not the owner of the specified post.");

        commentRepository.deleteByPost(post);
        postRepository.delete(post);
    }

    @Transactional
    public void deleteComment(DeleteCommentRequest commentDto) {
        Comment comment = commentRepository.findById(commentDto.getCommentId()).orElse(null);
        if (comment == null)
            throw new RuntimeException("Comment removal failed! The specified comment doesn't exist.");

        ValidateUserResponse validation = this.validateUser(commentDto.getUsername(), commentDto.getPassword());
        if (!validation.isValid() || validation.getUserId() != comment.getUserId())
            throw new RuntimeException("Comment removal failed! You are not the owner of the specified comment.");

        commentRepository.delete(comment);
    }

    public List<PostInfoResponse> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        List<PostInfoResponse> result = new ArrayList<>();
        for (Post post : posts) {

            // Get the username of the post owner by making a request to the user microservice.
            ResponseEntity<UserInfoResponse> infoRespEntity = userClient.getUserInfo(post.getUserId());
            UserInfoResponse userInfo = infoRespEntity.getBody();
            if (userInfo == null)
                throw new RuntimeException("Posts retrieval failed! Couldn't get the owner username for one or more posts.");

            PostInfoResponse info = postDtoMapper.toPostInfoResponse(post);
            info.setUsername(userInfo.getUsername());
            result.add(info);
        }
        return result;
    }

    public List<CommentInfoResponse> getCommentsForPost(int postId) {
        Post post = postRepository.findById(postId).orElse(null);
        if (post == null)
            throw new RuntimeException("Post comments retrieval failed! The specified post doesn't exist.");

        List<Comment> comments = commentRepository.findAllByPostOrderByTimestampAsc(post);
        List<CommentInfoResponse> result = new ArrayList<>();
        for (Comment comment : comments) {

            // Get the username of the comment owner by making a request to the user microservice.
            ResponseEntity<UserInfoResponse> infoRespEntity = userClient.getUserInfo(comment.getUserId());
            UserInfoResponse userInfo = infoRespEntity.getBody();
            if (userInfo == null)
                throw new RuntimeException("Post comments retrieval failed! Couldn't get the owner username for one or more comments.");

            CommentInfoResponse info = postDtoMapper.toCommentInfoResponse(comment);
            info.setUsername(userInfo.getUsername());
            result.add(info);
        }
        return result;
    }

    private ValidateUserResponse validateUser(String username, String password) {
        // Check if the user credentials are valid by making a request to the User microservice.
        // If the credentials are valid, the request returns the user ID.
        ValidateUserRequest validateReq = new ValidateUserRequest(username, password);
        ResponseEntity<ValidateUserResponse> validateRespEntity = userClient.isUserValid(validateReq);
        ValidateUserResponse validateResp = validateRespEntity.getBody();
        if (validateResp == null)
            throw new RuntimeException("Operation failed! Couldn't verify if the user credentials are valid.");

        return validateResp;
    }
}
