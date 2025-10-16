package com.jayesh.bookmyshow.controller;

import com.jayesh.bookmyshow.dto.request.ShowRequestDto;
import com.jayesh.bookmyshow.dto.response.ShowResponseDto;
import com.jayesh.bookmyshow.service.ShowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/shows")
public class ShowController {
    private final ShowService showService;


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

    @PostMapping
    public ResponseEntity<ShowResponseDto> put(@RequestBody ShowRequestDto request){
        ShowResponseDto response = showService.createShow(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/bulk")
    public ResponseEntity<List<ShowResponseDto>> put(@RequestBody List<ShowRequestDto> request){
        List<ShowResponseDto> response = showService.createShowsInBulk(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ShowResponseDto> updateShowDetails(@PathVariable Long id,@RequestBody ShowRequestDto request) {
        ShowResponseDto response = showService.updateShowDetails(id, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

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
