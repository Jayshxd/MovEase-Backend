package com.jayesh.bookmyshow.dto.response;

import com.jayesh.bookmyshow.entities.Show;
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
public class ShowResponseDto {

    private Long showId;
    private LocalDate showDate;
    private LocalTime showTime;
    private double ticketPrice;
    private Long movieId;
    private Long screenId;

    public ShowResponseDto(Show show){
        this.showId = show.getId();
        this.showDate = show.getShowDate();
        this.showTime=show.getShowTime();
        this.ticketPrice=show.getTicketPrice();
        this.movieId=show.getMovie().getId();
        this.screenId=show.getScreen().getId();
    }
}
