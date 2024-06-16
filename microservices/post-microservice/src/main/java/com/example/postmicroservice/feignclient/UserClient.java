package com.example.postmicroservice.feignclient;

import com.example.postmicroservice.dto.feignclient.UserInfoResponse;
import com.example.postmicroservice.dto.feignclient.ValidateUserRequest;
import com.example.postmicroservice.dto.feignclient.ValidateUserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name="user-microservice",
        url="http://localhost:8080",
        path="/user",
        configuration=UserClientConfig.class
)
public interface UserClient {
    @GetMapping("/isvalid")
    public ResponseEntity<ValidateUserResponse> isUserValid(@RequestBody ValidateUserRequest req);

    @GetMapping("/get/{id}")
    public ResponseEntity<UserInfoResponse> getUserInfo(@PathVariable("id") int userId);
}
