package com.jayesh.bookmyshow.dto.response;

import com.jayesh.bookmyshow.entities.Screen;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(
        name = "ScreenResponseDto",
        description = "Response object containing screen details, including name, total seats, and linked theatre ID."
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ScreenResponseDto {

    @Schema(description = "Unique identifier of the screen.", example = "3")
    private Long id;

    @Schema(description = "Name of the screen (like Screen 1, IMAX, Gold Class).", example = "IMAX Gold")
    private String name;

    @Schema(description = "Total number of seats in this screen.", example = "180")
    private int totalSeats;

    @Schema(description = "ID of the theatre to which this screen belongs.", example = "9")
    private Long theatreId;

    public ScreenResponseDto(Screen screen) {
        this.id = screen.getId();
        this.name = screen.getName();
        this.totalSeats = screen.getTotalSeats();
        this.theatreId = screen.getTheatre().getId();
    }
}
