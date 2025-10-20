package com.jayesh.bookmyshow.controller;

import com.jayesh.bookmyshow.dto.request.ScreenRequestDto;
import com.jayesh.bookmyshow.dto.request.TheatreRequestDto;
import com.jayesh.bookmyshow.dto.response.ScreenResponseDto;
import com.jayesh.bookmyshow.dto.response.ShowResponseDto;
import com.jayesh.bookmyshow.dto.response.TheatreResponseDto;
import com.jayesh.bookmyshow.service.ScreenService;
import com.jayesh.bookmyshow.service.ShowService;
import com.jayesh.bookmyshow.service.TheatreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
@Tag(
        name = "Theatre Management APIs",
        description = "Manage theatres, their screens, and shows. Includes CRUD operations for theatres and related entities."
)

@RequiredArgsConstructor
@RestController
@RequestMapping("/theatres")
public class TheatreController {
    private final TheatreService theatreService;
    private final ScreenService screenService;
    private final ShowService showService;

    @Operation(
            summary = "Get all theatres with optional filters",
            description = "Retrieve a list of theatres filtered by name, city, or address. Accessible to all users."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Theatre list retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error while fetching theatres")
    })
    @GetMapping
    public ResponseEntity<List<TheatreResponseDto>> get(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String address
    ) {
        List<TheatreResponseDto> res = theatreService.findTheatres(name, city, address);
        return ResponseEntity.ok(res);
    }


    @Operation(
            summary = "Get theatre by ID",
            description = "Retrieve details of a specific theatre by its ID. Accessible to all users."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Theatre details fetched successfully"),
            @ApiResponse(responseCode = "404", description = "Theatre not found")
    })

    @GetMapping("/{id}")
    public ResponseEntity<TheatreResponseDto> getById(@PathVariable Long id) {
        TheatreResponseDto res = theatreService.getTheatreById(id);
        return ResponseEntity.ok(res);
    }



    @Operation(
            summary = "Create a new theatre",
            description = "Allows ADMIN to create a new theatre with basic information such as name, city, and address."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Theatre created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid theatre data"),
            @ApiResponse(responseCode = "403", description = "Access denied - only admins can create theatres")
    })

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<TheatreResponseDto> createATheatre(@RequestBody TheatreRequestDto req) {
        TheatreResponseDto res = theatreService.createTheatre(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(res);

    }


    @Operation(
            summary = "Create multiple theatres (bulk)",
            description = "Allows ADMIN to create multiple theatres in a single API call."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Theatres created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid bulk data format"),
            @ApiResponse(responseCode = "403", description = "Access denied - only admins can perform this operation")
    })

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/bulk")
    public ResponseEntity<List<TheatreResponseDto>> createTheatresInBulk(@RequestBody List<TheatreRequestDto> req) {
        List<TheatreResponseDto> res = theatreService.createTheatresInBulk(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }


    @Operation(
            summary = "Update theatre details (full update)",
            description = "Completely update all fields of an existing theatre. Only accessible to ADMIN role."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Theatre updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid theatre data"),
            @ApiResponse(responseCode = "404", description = "Theatre not found"),
            @ApiResponse(responseCode = "403", description = "Unauthorized access")
    })

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<TheatreResponseDto> put(@PathVariable Long id,@RequestBody TheatreRequestDto req) {
        TheatreResponseDto res  = theatreService.updateTheatreByPut(id,req);
        return ResponseEntity.ok(res);
    }


    @Operation(
            summary = "Partially update theatre details",
            description = "Allows partial updates of a theatre’s details (e.g., change city or address only). Only ADMIN can access this."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Theatre details updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid update request"),
            @ApiResponse(responseCode = "404", description = "Theatre not found"),
            @ApiResponse(responseCode = "403", description = "Unauthorized access")
    })

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}")
    public ResponseEntity<TheatreResponseDto> patch(@PathVariable Long id,@RequestBody TheatreRequestDto req) {
        TheatreResponseDto res  = theatreService.updateTheatreDetails(id,req);
        return ResponseEntity.ok(res);
    }


    @Operation(
            summary = "Delete a theatre by ID",
            description = "Deletes a theatre and its associated data. Only ADMIN can perform this action."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Theatre deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Theatre not found"),
            @ApiResponse(responseCode = "403", description = "Unauthorized access")
    })

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        theatreService.deleteTheatre(id);
        return ResponseEntity.noContent().build();
    }








    //Screen methods
    @Operation(
            summary = "Get all screens for a theatre",
            description = "Retrieve all screens associated with a specific theatre. Accessible to all users."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Screens retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Theatre not found")
    })

    @GetMapping("/{id}/screens")
    public ResponseEntity<List<ScreenResponseDto>> getAlLScreensOfATheatre(@PathVariable Long id) {
        List<ScreenResponseDto> res = screenService.getAllScreensForThisTheatre(id);
        return ResponseEntity.ok(res);
    }


    @Operation(
            summary = "Create a screen for a theatre",
            description = "Allows ADMIN to create a new screen within a specific theatre."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Screen created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid screen data"),
            @ApiResponse(responseCode = "403", description = "Unauthorized access")
    })

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{theatreId}/screens")
    public ResponseEntity<ScreenResponseDto> createScreenForTheatre(
            @PathVariable Long theatreId,
            @RequestBody ScreenRequestDto req) {

        // The TheatreController calls the ScreenService directly
        ScreenResponseDto res = screenService.createAScreen(theatreId, req);
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }


    @Operation(
            summary = "Create multiple screens (bulk) for a theatre",
            description = "Allows ADMIN to add multiple screens to a theatre in one go."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Screens created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid bulk screen data"),
            @ApiResponse(responseCode = "403", description = "Unauthorized access")
    })

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{theatreId}/screens/bulk")
    public ResponseEntity<List<ScreenResponseDto>> createScreensInBulkForTheatre(
            @PathVariable Long theatreId,
            @RequestBody List<ScreenRequestDto> req) {

        List<ScreenResponseDto> res = screenService.createScreensInBulk(theatreId, req);
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }



    @Operation(
            summary = "Delete all screens of a theatre",
            description = "Removes all screens associated with a specific theatre. Only ADMIN can access this."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "All screens deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Theatre not found"),
            @ApiResponse(responseCode = "403", description = "Unauthorized access")
    })

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}/screens")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        screenService.deleteAllScreensOfATheatre(id);
        return ResponseEntity.noContent().build();
    }




    //all shows in a theater
    @Operation(
            summary = "Get all shows in a theatre",
            description = "Fetch all shows scheduled in a specific theatre on a given date."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Shows retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Theatre not found or no shows available"),
            @ApiResponse(responseCode = "400", description = "Invalid date format")
    })

    @GetMapping("/{id}/shows")
    public ResponseEntity<List<ShowResponseDto>> getAllShowsInATheatre(@PathVariable Long id,@RequestParam LocalDate date) {
        List<ShowResponseDto> response =  showService.findAllShowsInATheatre(id, date);
        return ResponseEntity.ok(response);
    }



}
