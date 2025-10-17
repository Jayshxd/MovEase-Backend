package com.jayesh.bookmyshow.controller;

import com.jayesh.bookmyshow.dto.request.BookingRequestDto;
import com.jayesh.bookmyshow.dto.response.BookingResponseDto;
import com.jayesh.bookmyshow.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RequiredArgsConstructor
@RestController
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;
    @PostMapping("/book")
    public ResponseEntity<BookingResponseDto> post(@RequestBody BookingRequestDto value) {
        BookingResponseDto bookingResponseDto = bookingService.createBooking(value);
        return ResponseEntity.status(HttpStatus.CREATED).body(bookingResponseDto);
    }
}
