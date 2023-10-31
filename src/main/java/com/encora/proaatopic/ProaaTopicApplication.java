package com.encora.proaatopic;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SecurityScheme(type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT", name = "Authorization", description = "Bearer token", in = SecuritySchemeIn.HEADER)
@SpringBootApplication
public class ProaaTopicApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProaaTopicApplication.class, args);
    }

}
