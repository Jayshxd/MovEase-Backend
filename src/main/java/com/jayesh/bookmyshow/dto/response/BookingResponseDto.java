package com.jayesh.bookmyshow.dto.response;

import com.jayesh.bookmyshow.entities.Booking;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Response DTO for a user's booking details")
public class BookingResponseDto {

    @Schema(description = "Time when the booking was made", example = "19:45:00")
    private LocalTime bookingTime;

    @Schema(description = "Total amount paid for the booking", example = "750.0")
    private double totalAmount;

    @Schema(description = "ID of the user who made the booking", example = "7")
    private Long userId;

    @Schema(description = "ID of the show booked", example = "14")
    private Long showId;

    @Schema(description = "List of booked seats in the show")
    private List<ShowSeatResponseDto> userSeats;

    @Schema(description = "Current status of the booking", example = "CONFIRMED")
    private String bookingStatus;

    public BookingResponseDto(Booking booking){
        this.bookingTime = booking.getBookingTime();
        this.totalAmount = booking.getTotalAmount();
        this.userId = booking.getUser().getId();
        this.showId = booking.getShow().getId();
        this.userSeats = booking.getShowSeats()
                .stream()
                .map(ShowSeatResponseDto::new)
                .collect(Collectors.toList());
        this.bookingStatus = booking.getBookingStatus();
    }
}
