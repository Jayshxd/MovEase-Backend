package com.jayesh.bookmyshow.dto.response;

import com.jayesh.bookmyshow.entities.Booking;
import com.jayesh.bookmyshow.entities.ShowSeat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookingResponseDto {
    private LocalTime bookingTime;
    private double totalAmount;
    private Long userId;
    private Long showId;
    private List<ShowSeatResponseDto> userSeats;
    private String bookingStatus;

    public BookingResponseDto(Booking booking){
        this.bookingTime=booking.getBookingTime();
        this.totalAmount=booking.getTotalAmount();
        this.userId=booking.getUser().getId();
        this.showId=booking.getShow().getId();
        this.userSeats= booking.getShowSeats().stream().map(ShowSeatResponseDto::new).collect(Collectors.toList());
        this.bookingStatus=booking.getBookingStatus();
    }

}
