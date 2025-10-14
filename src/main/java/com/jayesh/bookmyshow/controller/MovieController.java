package com.jayesh.bookmyshow.controller;

import com.jayesh.bookmyshow.dto.request.MovieRequestDto;
import com.jayesh.bookmyshow.dto.response.MovieResponseDto;
import com.jayesh.bookmyshow.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;

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


    @PostMapping
    public ResponseEntity<MovieResponseDto> post(@RequestBody MovieRequestDto req) {
        MovieResponseDto res = movieService.createMovie(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieResponseDto> put(@PathVariable Long id, @RequestBody MovieRequestDto req) {
        MovieResponseDto res = movieService.updateByPut(id,req);
        return ResponseEntity.ok(res);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        movieService.deleteMovie(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieResponseDto> getMovie(@PathVariable Long id) {
        MovieResponseDto res = movieService.getMovieById(id);
        return ResponseEntity.ok(res);

    }

    @PostMapping("/bulk")
    public ResponseEntity<List<MovieResponseDto>> post(@RequestBody List<MovieRequestDto> req) {
        List<MovieResponseDto> res = movieService.createMoviesInBulk(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

}
