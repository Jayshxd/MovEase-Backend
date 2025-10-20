package com.jayesh.bookmyshow.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(name = "LoginRequestDto", description = "Used for user authentication.")
@Getter
@Setter
public class LoginRequestDto {

    @Schema(description = "Username of the user.", example = "jayesh123")
    private String username;

    @Schema(description = "Password of the user.", example = "securePass@123")
    private String password;
}
