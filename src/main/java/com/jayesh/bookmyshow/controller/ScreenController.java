package com.jayesh.bookmyshow.controller;

import com.jayesh.bookmyshow.dto.request.ScreenRequestDto;
import com.jayesh.bookmyshow.dto.request.SeatRequestDto;
import com.jayesh.bookmyshow.dto.response.ScreenResponseDto;
import com.jayesh.bookmyshow.dto.response.SeatResponseDto;
import com.jayesh.bookmyshow.dto.response.ShowResponseDto;
import com.jayesh.bookmyshow.service.ScreenService;
import com.jayesh.bookmyshow.service.SeatService;
import com.jayesh.bookmyshow.service.ShowService;
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

import java.time.LocalDate;
import java.util.List;
@Tag(
        name = "Screen Management APIs",
        description = "APIs for managing cinema screens, their seats, and scheduled shows. Includes seat and show-level operations for each screen."
)
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/screens")
@RequiredArgsConstructor
public class ScreenController {

    private final ScreenService screenService;
    private final SeatService seatService;
    private final ShowService showService;


    @Operation(
            summary = "Get all screens",
            description = "Retrieves a list of all available cinema screens in the system along with their details."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of screens retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error while fetching screens")
    })

    @GetMapping
    public ResponseEntity<List<ScreenResponseDto>> getALlTheScreens() {
        List<ScreenResponseDto> res = screenService.getAllScreens();
        return ResponseEntity.ok(res);
    }


    @Operation(
            summary = "Update a screen (Full Update)",
            description = "Allows ADMIN to update all details of a specific screen using PUT method."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Screen updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid screen data"),
            @ApiResponse(responseCode = "403", description = "Access denied - only admins can update screens")
    })

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ScreenResponseDto> put(@RequestBody ScreenRequestDto req, @PathVariable Long id) {
        ScreenResponseDto res = screenService.updateByPut(id, req);
        return ResponseEntity.ok(res);
    }


    @Operation(
            summary = "Update specific screen fields (Partial Update)",
            description = "Allows ADMIN to update only selected fields of a screen, such as name or capacity."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Screen details updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "403", description = "Access denied - only admins can modify screen details")
    })

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}")
    public ResponseEntity<ScreenResponseDto> patch(@RequestBody ScreenRequestDto req, @PathVariable Long id) {
        ScreenResponseDto res = screenService.updateScreenDetails(id, req);
        return ResponseEntity.ok(res);
    }


    @Operation(
            summary = "Delete a screen",
            description = "Deletes a screen and its related seats/shows if applicable. Only ADMIN can perform this operation."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Screen deleted successfully"),
            @ApiResponse(responseCode = "403", description = "Access denied - only admins can delete screens"),
            @ApiResponse(responseCode = "404", description = "Screen not found")
    })

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        screenService.deleteScreen(id);
        return ResponseEntity.ok().build();
    }




    //seats............you were here
    //ek screen ki saari seats
    @Operation(
            summary = "Get all seats for a specific screen",
            description = "Fetches all seats linked to a particular screen. You can filter by seat number or seat type."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Seats fetched successfully"),
            @ApiResponse(responseCode = "404", description = "Screen or seats not found")
    })

    @GetMapping("/{id}/seats")
    public ResponseEntity<List<SeatResponseDto>> getAllSeats(
            @PathVariable Long id,
            @RequestParam(required = false) String seatNumber,
            @RequestParam(required = false) String seatType
            ) {
        List<SeatResponseDto> res  = seatService.findAllSeatsForAScreen(id,seatNumber,seatType);
        return ResponseEntity.ok(res);
    }


    @Operation(
            summary = "Create a new seat for a screen",
            description = "Allows ADMIN to add a new seat to a specific screen."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Seat created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid seat details"),
            @ApiResponse(responseCode = "403", description = "Access denied - only admins can create seats")
    })

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{id}/seats")
    public ResponseEntity<SeatResponseDto> createSeat(@PathVariable Long id, @RequestBody SeatRequestDto req) {
        SeatResponseDto res = seatService.createSeat(id,req);
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    @Operation(
            summary = "Bulk create seats for a screen",
            description = "Allows ADMIN to create multiple seats for a screen in a single request."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Seats created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid seat list or structure"),
            @ApiResponse(responseCode = "403", description = "Access denied - only admins can perform this operation")
    })

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{id}/seats/bulk")
    public ResponseEntity<List<SeatResponseDto>> createSeat(@PathVariable Long id, @RequestBody List<SeatRequestDto> req) {
        List<SeatResponseDto> res = seatService.createSeatsInBulk(id, req);
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }



    //shows........
    @Operation(
            summary = "Get all shows of a screen",
            description = "Fetches all shows scheduled for a specific screen on a given date."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Shows fetched successfully"),
            @ApiResponse(responseCode = "404", description = "Screen or shows not found")
    })

    @GetMapping("/{id}/shows")
    public ResponseEntity<List<ShowResponseDto>> getAllShowsOfAScreen(@PathVariable Long id,@RequestParam LocalDate showDate) {
        List<ShowResponseDto> response = showService.findAllShowsOfAScreen(id,showDate);
        return ResponseEntity.ok(response);

    }


}
