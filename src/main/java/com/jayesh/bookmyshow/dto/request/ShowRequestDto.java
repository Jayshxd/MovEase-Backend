package com.jayesh.bookmyshow.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
@Schema(name = "ShowRequestDto", description = "Used to schedule a show for a movie on a particular date and time.")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShowRequestDto {

    @Schema(description = "Date of the show.", example = "2025-05-10")
    private LocalDate showDate;

    @Schema(description = "Time of the show.", example = "19:30:00")
    private LocalTime showTime;

    @Schema(description = "Base ticket price for the show.", example = "250.0")
    private double ticketPrice;

    @Schema(description = "Movie ID for which the show is being created.", example = "3")
    private Long movieId;

    @Schema(description = "Screen ID where the show will be displayed.", example = "5")
    private Long screenId;
}
