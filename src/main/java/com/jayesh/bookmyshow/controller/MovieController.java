package com.jayesh.bookmyshow.controller;

import com.jayesh.bookmyshow.dto.request.MovieRequestDto;
import com.jayesh.bookmyshow.dto.response.MovieResponseDto;
import com.jayesh.bookmyshow.dto.response.ShowResponseDto;
import com.jayesh.bookmyshow.service.MovieService;
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
        name = "Movie Management APIs",
        description = "Endpoints for managing movies, including creating, updating, deleting, and retrieving movies and their associated shows."
)
@SecurityRequirement(name = "bearerAuth")

@RequiredArgsConstructor
@RestController
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;
    private final ShowService showService;


    @Operation(
            summary = "Get all movies (with optional filters)",
            description = "Fetches all movies available in the system. You can filter by title, genre, language, duration, description, or release date."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Movies fetched successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error while fetching movies")
    })

    @GetMapping
    public ResponseEntity<List<MovieResponseDto>> get(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) String language,
            @RequestParam(required = false) String duration,
            @RequestParam(required = false) LocalDate releaseDate
    ) {
        List<MovieResponseDto> res = movieService.findMovies(title,description,genre,language,duration,releaseDate);
        return ResponseEntity.ok(res);
    }



    @Operation(
            summary = "Create a new movie",
            description = "Allows ADMIN to add a new movie with its details like title, genre, language, duration, and release date."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Movie created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid movie data provided"),
            @ApiResponse(responseCode = "403", description = "Access denied - only admins can create movies")
    })

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<MovieResponseDto> post(@RequestBody MovieRequestDto req) {
        MovieResponseDto res = movieService.createMovie(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }


    @Operation(
            summary = "Update movie details (Full Update)",
            description = "Allows ADMIN to update all details of an existing movie. Replaces existing data completely."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Movie updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid movie update data"),
            @ApiResponse(responseCode = "403", description = "Access denied - only admins can update movies"),
            @ApiResponse(responseCode = "404", description = "Movie not found")
    })

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<MovieResponseDto> put(@PathVariable Long id, @RequestBody MovieRequestDto req) {
        MovieResponseDto res = movieService.updateByPut(id,req);
        return ResponseEntity.ok(res);
    }


    @Operation(
            summary = "Delete a movie",
            description = "Deletes a specific movie from the system. Only ADMIN can perform this operation."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Movie deleted successfully"),
            @ApiResponse(responseCode = "403", description = "Access denied - only admins can delete movies"),
            @ApiResponse(responseCode = "404", description = "Movie not found")
    })

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        movieService.deleteMovie(id);
        return ResponseEntity.noContent().build();
    }



    @Operation(
            summary = "Get a movie by ID",
            description = "Retrieves detailed information of a single movie using its unique ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Movie fetched successfully"),
            @ApiResponse(responseCode = "404", description = "Movie not found")
    })

    @GetMapping("/{id}")
    public ResponseEntity<MovieResponseDto> getMovie(@PathVariable Long id) {
        MovieResponseDto res = movieService.getMovieById(id);
        return ResponseEntity.ok(res);

    }



    @Operation(
            summary = "Bulk create movies",
            description = "Allows ADMIN to add multiple movies in a single request for faster setup."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Movies created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid movie list data"),
            @ApiResponse(responseCode = "403", description = "Access denied - only admins can bulk create movies")
    })

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/bulk")
    public ResponseEntity<List<MovieResponseDto>> post(@RequestBody List<MovieRequestDto> req) {
        List<MovieResponseDto> res = movieService.createMoviesInBulk(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }




    @Operation(
            summary = "Get all shows for a movie",
            description = "Fetches all scheduled shows associated with a specific movie."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Shows fetched successfully"),
            @ApiResponse(responseCode = "404", description = "Movie or shows not found")
    })

    @GetMapping("/{id}/shows")
    public ResponseEntity<List<ShowResponseDto>> getShows(@PathVariable Long id) {
        List<ShowResponseDto> response = showService.findAllShowsForAMovie(id);
        return ResponseEntity.ok(response);
    }


}
