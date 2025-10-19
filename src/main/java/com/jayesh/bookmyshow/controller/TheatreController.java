package com.jayesh.bookmyshow.controller;

import com.jayesh.bookmyshow.dto.request.ScreenRequestDto;
import com.jayesh.bookmyshow.dto.request.TheatreRequestDto;
import com.jayesh.bookmyshow.dto.response.ScreenResponseDto;
import com.jayesh.bookmyshow.dto.response.ShowResponseDto;
import com.jayesh.bookmyshow.dto.response.TheatreResponseDto;
import com.jayesh.bookmyshow.service.ScreenService;
import com.jayesh.bookmyshow.service.ShowService;
import com.jayesh.bookmyshow.service.TheatreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/theatres")
public class TheatreController {
    private final TheatreService theatreService;
    private final ScreenService screenService;
    private final ShowService showService;

    @GetMapping
    public ResponseEntity<List<TheatreResponseDto>> get(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String address
    ) {
        List<TheatreResponseDto> res = theatreService.findTheatres(name, city, address);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TheatreResponseDto> getById(@PathVariable Long id) {
        TheatreResponseDto res = theatreService.getTheatreById(id);
        return ResponseEntity.ok(res);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<TheatreResponseDto> createATheatre(@RequestBody TheatreRequestDto req) {
        TheatreResponseDto res = theatreService.createTheatre(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(res);

    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/bulk")
    public ResponseEntity<List<TheatreResponseDto>> createTheatresInBulk(@RequestBody List<TheatreRequestDto> req) {
        List<TheatreResponseDto> res = theatreService.createTheatresInBulk(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<TheatreResponseDto> put(@PathVariable Long id,@RequestBody TheatreRequestDto req) {
        TheatreResponseDto res  = theatreService.updateTheatreByPut(id,req);
        return ResponseEntity.ok(res);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}")
    public ResponseEntity<TheatreResponseDto> patch(@PathVariable Long id,@RequestBody TheatreRequestDto req) {
        TheatreResponseDto res  = theatreService.updateTheatreDetails(id,req);
        return ResponseEntity.ok(res);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        theatreService.deleteTheatre(id);
        return ResponseEntity.noContent().build();
    }








    //Screen methods

    @GetMapping("/{id}/screens")
    public ResponseEntity<List<ScreenResponseDto>> getAlLScreensOfATheatre(@PathVariable Long id) {
        List<ScreenResponseDto> res = screenService.getAllScreensForThisTheatre(id);
        return ResponseEntity.ok(res);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{theatreId}/screens")
    public ResponseEntity<ScreenResponseDto> createScreenForTheatre(
            @PathVariable Long theatreId,
            @RequestBody ScreenRequestDto req) {

        // The TheatreController calls the ScreenService directly
        ScreenResponseDto res = screenService.createAScreen(theatreId, req);
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{theatreId}/screens/bulk")
    public ResponseEntity<List<ScreenResponseDto>> createScreensInBulkForTheatre(
            @PathVariable Long theatreId,
            @RequestBody List<ScreenRequestDto> req) {

        List<ScreenResponseDto> res = screenService.createScreensInBulk(theatreId, req);
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}/screens")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        screenService.deleteAllScreensOfATheatre(id);
        return ResponseEntity.noContent().build();
    }




    //all shows in a theater
    @GetMapping("/{id}/shows")
    public ResponseEntity<List<ShowResponseDto>> getAllShowsInATheatre(@PathVariable Long id,@RequestParam LocalDate date) {
        List<ShowResponseDto> response =  showService.findAllShowsInATheatre(id, date);
        return ResponseEntity.ok(response);
    }



}
