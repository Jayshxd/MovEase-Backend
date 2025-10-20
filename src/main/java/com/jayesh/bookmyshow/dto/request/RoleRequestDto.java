package com.jayesh.bookmyshow.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Schema(name = "RoleRequestDto", description = "Used to assign or update roles for a user.")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleRequestDto {

    @Schema(description = "User ID for whom roles are being assigned.", example = "10")
    private Long userId;

    @Schema(description = "Set of roles to assign to the user.", example = "[\"ADMIN\", \"USER\"]")
    private Set<String> roles;
}
