package com.jayesh.bookmyshow.dto.response;

import com.jayesh.bookmyshow.entities.ShowSeat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShowSeatResponseDto {
    private Long id;
    private String status;
    private Double price;
    private Long seatId;
    private Long showId;

    public ShowSeatResponseDto(ShowSeat showSeat){
        this.id = showSeat.getId();
        this.status = showSeat.getStatus();
        this.price = showSeat.getPrice();
        this.seatId = showSeat.getSeat().getId();
        this.showId = showSeat.getShow().getId();
    }
}
