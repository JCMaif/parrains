package com.simplon.parrains.config;


import org.springframework.context.annotation.Bean;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;

public class SwaggerConfig {

        @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                    .title("API Documentation")
                    .version("1.0")
                    .description("Documentation de l'API pour le projet"));
    }
}
