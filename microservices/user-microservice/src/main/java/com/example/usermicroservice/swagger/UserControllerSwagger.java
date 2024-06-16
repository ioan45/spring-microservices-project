package com.example.usermicroservice.swagger;

import com.example.usermicroservice.dto.*;
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

import java.util.List;

@Tag(name = "User", description = "API to manage user accounts.")
public interface UserControllerSwagger {
    @Operation(
            summary = "Sign up a new user.",
            description = "Add a new user to the system. The response should be a message and a HATEOAS link to get the user information.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = SignUpUserResponse.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema(implementation = String.class), mediaType = "text/plain") }) })
    public ResponseEntity<?> signUpUser(@RequestBody SignUpUserRequest userData);

    @Operation(
            summary = "Check if credentials are valid.",
            description = "Checks if the given user credentials are valid. Endpoint used by the POSTS microservice.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = ValidateUserResponse.class), mediaType = "application/json") }) })
    public ResponseEntity<ValidateUserResponse> isUserValid(@RequestBody ValidateUserRequest userData);

    @Operation(
            summary = "Get all registered users.",
            description = "Get a list with all the users registered in the system.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(array = @ArraySchema(schema = @Schema(implementation = UserInfoResponse.class)), mediaType = "application/json") }) })
    public ResponseEntity<List<UserInfoResponse>> getAllUsers();

    @Operation(
            summary = "Get user data.",
            description = "Get the user information registered for the given user ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = UserInfoResponse.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema(implementation = String.class), mediaType = "text/plain") }) })
    public ResponseEntity<?> getUserInfo(@PathVariable("id") int userId);
}
