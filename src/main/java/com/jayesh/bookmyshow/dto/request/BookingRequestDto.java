package com.jayesh.bookmyshow.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Schema(name = "BookingRequestDto", description = "Used for creating a new booking by a user for a specific show.")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookingRequestDto {

    @Schema(description = "User ID making the booking.", example = "7")
    private Long userId;

    @Schema(description = "Show ID for which the booking is made.", example = "12")
    private Long showId;

    @Schema(description = "List of selected ShowSeat IDs.", example = "[101, 102, 103]")
    private List<Long> showSeatIds;
}
