package com.jayesh.bookmyshow.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(
        name = "ScreenRequestDto",
        description = "Request object used to create or update a screen inside a theatre with basic details like name and total number of seats."
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ScreenRequestDto {

    @Schema(
            description = "Name of the screen (e.g., Screen 1, IMAX, Gold Class).",
            example = "Screen 1"
    )
    private String name;

    @Schema(
            description = "Total number of seats available in this screen.",
            example = "120"
    )
    private int totalSeats;
}
