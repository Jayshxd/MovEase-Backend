package com.jayesh.bookmyshow.dto.response;

import com.jayesh.bookmyshow.entities.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Schema(
        name = "UserResponseDto",
        description = "Response object returned after performing user-related operations such as registration, login, or role assignment."
)

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
    @Schema(description = "Unique identifier of the user.", example = "101")
    private Long id;

    @Schema(description = "Full name of the user.", example = "Jayesh Hiwarkar")
    private String name;

    @Schema(description = "Custom response message related to the operation performed.", example = "User created successfully.")
    private String message;


    public UserResponseDto(User user,String message) {
        this.id = user.getId();
        this.name = user.getName();
        this.message = message;
    }

}
