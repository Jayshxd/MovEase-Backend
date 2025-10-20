package com.jayesh.bookmyshow.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        // Yeh humari security scheme ka naam hai
        final String securitySchemeName = "bearerAuth";
        return new OpenAPI()
                // Step 1: Components me security scheme define karo
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName, new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP) // Type HTTP hai
                                .scheme("bearer") // Scheme bearer hai
                                .bearerFormat("JWT") // Format JWT hai
                                .description("Enter JWT Bearer token")
                        )
                )
                // Step 2: Saare endpoints ke liye is security requirement ko add karo
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .info(
                new Info().title("MovEase Backend APIs")
                        .version("1.2")
                .description("Version 1.0 : Had APIs that were working fine" +
                        "Version 1.1 : Added basic security" +
                        "Version 1.2 : Added Role Based Access Control and JWT Authentication" +
                        "These are few main APIs that are working more detailed are in progress..")
                        .contact(
                                new Contact()
                                        .name("Jayesh Hiwarkar")
                                        .email("jayeshhiwarkar.work@gmail.com")
                        )

        );
    }
}
