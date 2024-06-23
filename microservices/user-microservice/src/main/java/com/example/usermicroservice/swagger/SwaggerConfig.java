package com.example.usermicroservice.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI config() {
        Server server = new Server();
        server.setUrl("http://localhost:8080");
        server.setDescription("Server URL in development environment");

        Contact contact = new Contact();
        contact.setName("ioan45");
        contact.setUrl("https://github.com/ioan45/spring-microservices-project");

        License mitLicense = new License().name("MIT License").url("https://github.com/ioan45/spring-microservices-project/blob/main/LICENSE");

        Info info = new Info()
                .title("Users API")
                .version("1.0")
                .contact(contact)
                .description("This API exposes endpoints to manage user accounts.")
                .license(mitLicense);

        return new OpenAPI().info(info).servers(List.of(server));
    }
}
