package com.jayesh.bookmyshow.dto.response;

import com.jayesh.bookmyshow.entities.Seat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(
        name = "SeatResponseDto",
        description = "Response object containing seat details such as seat number, seat type, and associated screen ID."
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SeatResponseDto {

    @Schema(description = "Unique identifier of the seat.", example = "201")
    private Long id;

    @Schema(description = "Seat number or label in the theatre.", example = "A10")
    private String seatNumber;

    @Schema(description = "Type of the seat, such as REGULAR, PREMIUM, or VIP.", example = "PREMIUM")
    private String seatType;

    @Schema(description = "ID of the screen this seat belongs to.", example = "7")
    private Long screenId;

    public SeatResponseDto(Seat seat) {
        this.id = seat.getId();
        this.seatNumber = seat.getSeatNumber();
        this.seatType = seat.getSeatType();
        this.screenId = seat.getScreen().getId();
    }
}
