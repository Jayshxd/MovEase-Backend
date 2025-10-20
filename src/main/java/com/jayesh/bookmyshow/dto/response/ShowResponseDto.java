package com.jayesh.bookmyshow.dto.response;

import com.jayesh.bookmyshow.entities.Show;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Schema(
        name = "ShowResponseDto",
        description = "Response object containing show details including date, time, ticket price, and associated movie and screen IDs."
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShowResponseDto {

    @Schema(description = "Unique identifier of the show.", example = "45")
    private Long showId;

    @Schema(description = "Date on which the show is scheduled.", example = "2025-10-20")
    private LocalDate showDate;

    @Schema(description = "Time when the show starts.", example = "19:30:00")
    private LocalTime showTime;

    @Schema(description = "Ticket price for the show.", example = "250.00")
    private double ticketPrice;

    @Schema(description = "ID of the movie being shown.", example = "12")
    private Long movieId;

    @Schema(description = "ID of the screen where the show is running.", example = "5")
    private Long screenId;

    public ShowResponseDto(Show show){
        this.showId = show.getId();
        this.showDate = show.getShowDate();
        this.showTime = show.getShowTime();
        this.ticketPrice = show.getTicketPrice();
        this.movieId = show.getMovie().getId();
        this.screenId = show.getScreen().getId();
    }
}
