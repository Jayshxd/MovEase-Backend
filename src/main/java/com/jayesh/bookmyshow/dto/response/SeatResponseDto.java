package com.jayesh.bookmyshow.dto.response;

import com.jayesh.bookmyshow.entities.Screen;
import com.jayesh.bookmyshow.entities.Seat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SeatResponseDto {
    private Long id;
    private String seatNumber;
    private String seatType;
    private Long screenId;

    public SeatResponseDto(Seat seat) {
        this.id = seat.getId();
        this.seatNumber = seat.getSeatNumber();
        this.seatType = seat.getSeatType();
        this.screenId = seat.getScreen().getId();
    }
}
