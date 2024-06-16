package com.example.postmicroservice.feignclient;

import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;

public class UserClientConfig {

    @Bean
    public ErrorDecoder errorDecoder() {
        return new UserClientErrorDecoder();
    }
}
