package com.jayesh.bookmyshow.controller;

import com.jayesh.bookmyshow.dto.request.SeatRequestDto;
import com.jayesh.bookmyshow.dto.response.SeatResponseDto;
import com.jayesh.bookmyshow.service.SeatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(
        name = "Seat Management",
        description = "APIs for managing seats, including creation, updates, and deletion of seats in the system"
)

@RequiredArgsConstructor
@RestController
@RequestMapping("/seats")
public class SeatController {

    private final SeatService seatService;

    @Operation(
            summary = "Fetch all seats",
            description = "Retrieve a list of seats filtered by optional parameters such as seat number or seat type."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully fetched seat details"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })

    @GetMapping
    public ResponseEntity<List<SeatResponseDto>> findSeats(
            @RequestParam(required = false) String seatNumber,
            @RequestParam(required = false) String seatType
    ){
        List<SeatResponseDto> res = seatService.findSeatsInSystem(seatNumber, seatType);
        return ResponseEntity.ok(res);
    }


    @Operation(
            summary = "Update seat information",
            description = "Completely update seat details using the provided ID and request body."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Seat updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid seat data provided"),
            @ApiResponse(responseCode = "403", description = "Access denied – only ADMIN can update seat details"),
            @ApiResponse(responseCode = "404", description = "Seat not found")
    })

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<SeatResponseDto> updateSeat(@PathVariable Long id,@RequestBody SeatRequestDto seatRequestDto){
        SeatResponseDto res = seatService.updateSeat(id,seatRequestDto);
        return ResponseEntity.ok(res);
    }


    @Operation(
            summary = "Partially update seat details",
            description = "Update only selected fields of a seat record identified by ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Seat details updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request payload"),
            @ApiResponse(responseCode = "403", description = "Access denied – only ADMIN can perform this operation"),
            @ApiResponse(responseCode = "404", description = "Seat not found")
    })

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}")
    public ResponseEntity<SeatResponseDto> updateSeatDetails(@PathVariable Long id, @RequestBody SeatRequestDto seatRequestDto){
        SeatResponseDto res = seatService.updateSeatDetails(id,seatRequestDto);
        return ResponseEntity.ok(res);
    }


    @Operation(
            summary = "Delete a seat",
            description = "Remove a seat from the system using its ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Seat deleted successfully"),
            @ApiResponse(responseCode = "403", description = "Access denied – only ADMIN can delete seats"),
            @ApiResponse(responseCode = "404", description = "Seat not found")
    })

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        seatService.deleteSeat(id);
        return ResponseEntity.noContent().build();
    }

}
