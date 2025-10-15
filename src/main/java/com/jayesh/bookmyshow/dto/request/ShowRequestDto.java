package com.jayesh.bookmyshow.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShowRequestDto {
    private LocalDate showDate;
    private LocalTime showTime;
    private double ticketPrice;
    private Long movieId;
    private Long screenId;
}
