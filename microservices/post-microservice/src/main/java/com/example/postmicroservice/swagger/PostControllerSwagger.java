package com.example.postmicroservice.swagger;

import com.example.postmicroservice.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Post", description = "API to manage posts and comments created by users.")
public interface PostControllerSwagger {
    @Operation(
            summary = "Create a new post.",
            description = "Create a new post for the given user. The response includes HATEOAS link to view all posts.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = NewPostResponse.class), mediaType = "text/plain") }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema(implementation = String.class), mediaType = "text/plain") }) })
    public ResponseEntity<?> newPost(@RequestBody NewPostRequest req);

    @Operation(
            summary = "Create a new comment for a post.",
            description = "Create a new comment for a given post. The response includes HATEOAS link to view all comments of the given post.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = NewCommentResponse.class), mediaType = "text/plain") }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema(implementation = String.class), mediaType = "text/plain") }) })
    public ResponseEntity<?> newComment(@RequestBody NewCommentRequest req);

    @Operation(
            summary = "Delete a post.",
            description = "Delete an existing post. An bad request response if given if the post doesn't exist.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = String.class), mediaType = "text/plain") }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema(implementation = String.class), mediaType = "text/plain") }) })
    public ResponseEntity<?> deletePost(@RequestBody DeletePostRequest req);

    @Operation(
            summary = "Delete a comment.",
            description = "Delete an existing comment from a post. An bad request response if given if the comment doesn't exist.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = String.class), mediaType = "text/plain") }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema(implementation = String.class), mediaType = "text/plain") }) })
    public ResponseEntity<?> deleteComment(@RequestBody DeleteCommentRequest req);

    @Operation(
            summary = "Get all recorded posts.",
            description = "Get a list of with all the posts in the database. Each post shows a HATEOAS link to get all its comments.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(array = @ArraySchema(schema = @Schema(implementation = PostInfoResponse.class)), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema(implementation = String.class), mediaType = "text/plain") }) })
    public ResponseEntity<?> getAllPosts();

    @Operation(
            summary = "Get all recorded comments for a post.",
            description = "Get a list of with all the comments created for a given post.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(array = @ArraySchema(schema = @Schema(implementation = CommentInfoResponse.class)), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema(implementation = String.class), mediaType = "text/plain") }) })
    public ResponseEntity<?> getCommentsForPost(@PathVariable("id") int postId);
}
