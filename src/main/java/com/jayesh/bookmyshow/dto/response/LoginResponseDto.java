package com.jayesh.bookmyshow.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Schema(description = "Response DTO returned after successful login")
public class LoginResponseDto {

    @Schema(description = "JWT authentication token", example = "eyJhbGciOiJIUzI1NiIsInR5cCI...")
    private String token;

    @Schema(description = "Username of the logged-in user", example = "jayesh123")
    private String username;

    @Schema(description = "Custom login message", example = "Login successful")
    private String message;
}
