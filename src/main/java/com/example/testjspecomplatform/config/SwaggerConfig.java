package com.example.testjspecomplatform.config;

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
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("test-jsp-ecom platform API Documentation")
                        .version("1.0.0")
                        .description("REST API documentation for Product and User Management System")
                        .contact(new Contact()
                                .name("test-jsp-ecom platform Team")
                                .email("support@test-jspecom-platform.com")
                                .url("https://test-jspecom-platform.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8082")
                                .description("Development Server"),
                        new Server()
                                .url("https://api.test-jspecom-platform.com")
                                .description("Production Server")
                ));
    }
}
