package com.jayesh.bookmyshow.dto.request;

import com.jayesh.bookmyshow.entities.Screen;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
@Schema(
        name = "TheatreRequestDto",
        description = "Request object used to create or update a theatre with basic information like name, city, and address."
)

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TheatreRequestDto {
    @Schema(description = "Name of the theatre to be created or updated.", example = "PVR Icon")
    private String name;

    @Schema(description = "City where the theatre is located.", example = "Mumbai")
    private String city;

    @Schema(description = "Detailed address of the theatre.", example = "Phoenix Mall, Lower Parel, Mumbai - 400013")
    private String address;

}
