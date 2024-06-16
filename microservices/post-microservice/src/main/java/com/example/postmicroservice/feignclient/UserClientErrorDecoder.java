package com.example.postmicroservice.feignclient;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;

public class UserClientErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        HttpStatus status = HttpStatus.valueOf(response.status());
        switch (status) {
            case INTERNAL_SERVER_ERROR:
                return new RuntimeException("Posts operation failed! Something went wrong in the user microservice.");
            default:
                return new RuntimeException("Posts operation failed! Couldn't access user information through the user microservice.");
        }
    }
}
