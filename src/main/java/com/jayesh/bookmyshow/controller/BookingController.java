package com.jayesh.bookmyshow.controller;

import com.jayesh.bookmyshow.dto.request.BookingRequestDto;
import com.jayesh.bookmyshow.dto.response.BookingResponseDto;
import com.jayesh.bookmyshow.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PostMapping("/book")
    public ResponseEntity<BookingResponseDto> post(@RequestBody BookingRequestDto value) {
        BookingResponseDto bookingResponseDto = bookingService.createBooking(value);
        return ResponseEntity.status(HttpStatus.CREATED).body(bookingResponseDto);
    }
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PostMapping("/{id}")
    public String cancelBooking(@PathVariable Long id) {
        return bookingService.cancelBooking(id);
    }
}
