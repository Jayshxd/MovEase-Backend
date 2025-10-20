package com.jayesh.bookmyshow.dto.response;

import com.jayesh.bookmyshow.entities.ShowSeat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShowSeatResponseDto {

    @Schema(description = "Unique ID of the show seat", example = "101")
    private Long id;

    @Schema(description = "Current booking status of the seat", example = "BOOKED")
    private String status;

    @Schema(description = "Price of the seat", example = "250.0")
    private Double price;

    @Schema(description = "ID of the associated seat", example = "11")
    private Long seatId;

    @Schema(description = "ID of the associated show", example = "5")
    private Long showId;

    public ShowSeatResponseDto(ShowSeat showSeat) {
        this.id = showSeat.getId();
        this.status = showSeat.getStatus();
        this.price = showSeat.getPrice();
        this.seatId = showSeat.getSeat().getId();
        this.showId = showSeat.getShow().getId();
    }
}
