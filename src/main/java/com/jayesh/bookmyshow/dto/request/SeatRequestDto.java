package com.jayesh.bookmyshow.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(name = "SeatRequestDto", description = "Used for creating seats in a particular screen.")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SeatRequestDto {

    @Schema(description = "Seat number or code.", example = "A10")
    private String seatNumber;

    @Schema(description = "Type of the seat.", example = "VIP")
    private String seatType;
}
