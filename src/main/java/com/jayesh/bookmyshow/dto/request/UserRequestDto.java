package com.jayesh.bookmyshow.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Schema(
        name = "UserRequestDto",
        description = "Data object used to register or create a new user account in the system."
)

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {
    @Schema(description = "Full name of the user", example = "Jayesh Hiwarkar")
    private String name;

    @Schema(description = "Unique username for login", example = "jayesh_01")
    private String username;

    @Schema(description = "Valid email address of the user", example = "jayeshhiwarkar.work@gmail.com")
    private String email;

    @Schema(description = "Password for the user's account", example = "StrongPassword@123")
    private String password;

}
