package com.jayesh.bookmyshow.controller;

import com.jayesh.bookmyshow.dto.request.ShowSeatRequestDto;
import com.jayesh.bookmyshow.dto.response.ShowSeatResponseDto;
import com.jayesh.bookmyshow.service.ShowSeatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Tag(
        name = "Show Seat Management APIs",
        description = "Handles creation and configuration of seats for specific movie shows. Accessible only to ADMIN users."
)
@SecurityRequirement(name = "bearerAuth")

@RequiredArgsConstructor
@RestController
@RequestMapping("/showsSeats")
public class ShowSeatController {
    private final ShowSeatService showSeatService;
    @Operation(
            summary = "Create seats for a specific show",
            description = "Allows ADMIN to configure or add seats for a specific show with details like seat type, price, and layout."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Show seats created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid show seat data or missing fields"),
            @ApiResponse(responseCode = "403", description = "Access denied - only admins can create show seats"),
            @ApiResponse(responseCode = "500", description = "Internal server error while creating seats")
    })

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ShowSeatResponseDto> post(@RequestBody ShowSeatRequestDto value) {
        ShowSeatResponseDto responseDto = showSeatService.createSeat(value);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

}
