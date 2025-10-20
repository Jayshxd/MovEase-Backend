package com.jayesh.bookmyshow.controller;

import com.jayesh.bookmyshow.dto.request.BookingRequestDto;
import com.jayesh.bookmyshow.dto.response.BookingResponseDto;
import com.jayesh.bookmyshow.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
@Tag(
        name = "Booking Management APIs",
        description = "Handles movie ticket bookings and cancellations. Accessible to both ADMIN and USER roles."
)
@SecurityRequirement(name = "bearerAuth")

@RequiredArgsConstructor
@RestController
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;


    @Operation(
            summary = "Create a new booking",
            description = "Allows USER or ADMIN to create a new movie booking. Requires show ID, selected seats, and payment details."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Booking created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid booking request or seat unavailable"),
            @ApiResponse(responseCode = "401", description = "Unauthorized access - login required"),
            @ApiResponse(responseCode = "403", description = "Access denied - insufficient role permissions"),
            @ApiResponse(responseCode = "500", description = "Internal server error while creating booking")
    })

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PostMapping("/book")
    public ResponseEntity<BookingResponseDto> post(@RequestBody BookingRequestDto value) {
        BookingResponseDto bookingResponseDto = bookingService.createBooking(value);
        return ResponseEntity.status(HttpStatus.CREATED).body(bookingResponseDto);
    }



    @Operation(
            summary = "Cancel an existing booking",
            description = "Cancels an existing booking by its booking ID. Both ADMIN and USER can cancel their bookings depending on permissions."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Booking cancelled successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid booking ID or booking cannot be cancelled"),
            @ApiResponse(responseCode = "401", description = "Unauthorized access - login required"),
            @ApiResponse(responseCode = "403", description = "Access denied - insufficient permissions"),
            @ApiResponse(responseCode = "404", description = "Booking not found")
    })

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PostMapping("/{id}")
    public String cancelBooking(@PathVariable Long id) {
        return bookingService.cancelBooking(id);
    }
}
