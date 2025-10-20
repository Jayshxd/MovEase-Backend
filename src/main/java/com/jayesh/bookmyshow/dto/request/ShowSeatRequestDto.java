package com.jayesh.bookmyshow.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(name = "ShowSeatRequestDto", description = "Used for mapping seats with a show and setting price/status.")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShowSeatRequestDto {

    @Schema(description = "Current status of the seat.", example = "AVAILABLE")
    private String status;

    @Schema(description = "Price of this specific seat for the show.", example = "300.0")
    private Double price;

    @Schema(description = "Seat ID being linked to the show.", example = "55")
    private Long seatId;

    @Schema(description = "Show ID in which the seat exists.", example = "21")
    private Long showId;
}
