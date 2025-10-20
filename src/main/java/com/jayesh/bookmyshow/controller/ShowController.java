package com.jayesh.bookmyshow.controller;

import com.jayesh.bookmyshow.dto.request.ShowRequestDto;
import com.jayesh.bookmyshow.dto.response.ShowResponseDto;
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
import java.time.LocalTime;
import java.util.List;

@Tag(
        name = "Show Management APIs",
        description = "APIs for managing movie shows — including creation, filtering, updates, and deletion. Only ADMIN can modify show details, while users can view them."
)
@SecurityRequirement(name = "bearerAuth")

@RequiredArgsConstructor
@RestController
@RequestMapping("/shows")
public class ShowController {
    private final ShowService showService;

    @Operation(
            summary = "Retrieve shows with optional filters",
            description = "Fetch all shows, optionally filtered by show date, time, ticket price, movie ID, or screen ID. Accessible to all users."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Shows retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid filter parameters"),
            @ApiResponse(responseCode = "500", description = "Internal server error while fetching shows")
    })

    @GetMapping
    public ResponseEntity<List<ShowResponseDto>> findAllShows(
            @RequestParam(required = false) LocalDate showDate,
            @RequestParam(required = false) LocalTime showTime,
            @RequestParam(required = false) Double ticketPrice,
            @RequestParam(required = false) Long movieId,
            @RequestParam(required = false) Long screenId
    ) {
        List<ShowResponseDto> response = showService.findAllShows(showDate,showTime,ticketPrice,movieId,screenId);
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }


    @Operation(
            summary = "Create a new show",
            description = "Allows ADMIN to create a new movie show with details like movie ID, screen, show time, date, and ticket price."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Show created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid show data"),
            @ApiResponse(responseCode = "403", description = "Access denied - only admins can create shows"),
            @ApiResponse(responseCode = "409", description = "Show conflict - overlapping timing or screen already booked")
    })

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ShowResponseDto> put(@RequestBody ShowRequestDto request){
        ShowResponseDto response = showService.createShow(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @Operation(
            summary = "Create multiple shows in bulk",
            description = "Allows ADMIN to create multiple shows at once. Useful for scheduling daily or weekly shows."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Shows created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid show list format"),
            @ApiResponse(responseCode = "403", description = "Access denied - only admins can create shows in bulk")
    })

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/bulk")
    public ResponseEntity<List<ShowResponseDto>> put(@RequestBody List<ShowRequestDto> request){
        List<ShowResponseDto> response = showService.createShowsInBulk(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }



    @Operation(
            summary = "Update show details (partial update)",
            description = "Allows ADMIN to partially update details of an existing show such as timing, ticket price, or screen allocation."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Show details updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "Show not found"),
            @ApiResponse(responseCode = "403", description = "Access denied - only admins can update shows")
    })

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}")
    public ResponseEntity<ShowResponseDto> updateShowDetails(@PathVariable Long id,@RequestBody ShowRequestDto request) {
        ShowResponseDto response = showService.updateShowDetails(id, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @Operation(
            summary = "Delete a show by ID",
            description = "Removes an existing show and its related seats or bookings. Only ADMIN can perform this action."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Show deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Show not found"),
            @ApiResponse(responseCode = "403", description = "Access denied - only admins can delete shows")
    })

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        showService.deleteAShow(id);
        return ResponseEntity.noContent().build();
    }




    /*
    ShowController mein ek naya @GetMapping("/{showId}/seats") bana.

    Ek naya ShowSeatService bana.
    Ismein ek method getSeatStatusForShow(Long showId) bana,
    jo ShowSeat table se us showId ke saare records nikal kar layega.
     */

}
