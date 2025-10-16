package com.jayesh.bookmyshow.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShowSeatRequestDto {
    private String status;
    private Double price;
    private Long seatId;
    private Long showId;
}
