package com.jayesh.bookmyshow.controller;

import com.jayesh.bookmyshow.dto.request.ScreenRequestDto;
import com.jayesh.bookmyshow.dto.request.SeatRequestDto;
import com.jayesh.bookmyshow.dto.response.ScreenResponseDto;
import com.jayesh.bookmyshow.dto.response.SeatResponseDto;
import com.jayesh.bookmyshow.service.ScreenService;
import com.jayesh.bookmyshow.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/screens")
@RequiredArgsConstructor
public class ScreenController {

    private final ScreenService screenService;
    private final SeatService seatService;

    @GetMapping
    public ResponseEntity<List<ScreenResponseDto>> getALlTheScreens() {
        List<ScreenResponseDto> res = screenService.getAllScreens();
        return ResponseEntity.ok(res);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ScreenResponseDto> put(@RequestBody ScreenRequestDto req, @PathVariable Long id) {
        ScreenResponseDto res = screenService.updateByPut(id, req);
        return ResponseEntity.ok(res);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ScreenResponseDto> patch(@RequestBody ScreenRequestDto req, @PathVariable Long id) {
        ScreenResponseDto res = screenService.updateScreenDetails(id, req);
        return ResponseEntity.ok(res);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        screenService.deleteScreen(id);
        return ResponseEntity.ok().build();
    }

    //seats............you were here
    //ek screen ki saari seats
    @GetMapping("/{id}/seats")
    public ResponseEntity<List<SeatResponseDto>> getAllSeats(
            @PathVariable Long id,
            @RequestParam(required = false) String seatNumber,
            @RequestParam(required = false) String seatType
            ) {
        List<SeatResponseDto> res  = seatService.findAllSeatsForAScreen(id,seatNumber,seatType);
        return ResponseEntity.ok(res);
    }
    @PostMapping("/{id}/seats")
    public ResponseEntity<SeatResponseDto> createSeat(@PathVariable Long id, @RequestBody SeatRequestDto req) {
        SeatResponseDto res = seatService.createSeat(id,req);
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }
    @PostMapping("/{id}/seats/bulk")
    public ResponseEntity<List<SeatResponseDto>> createSeat(@PathVariable Long id, @RequestBody List<SeatRequestDto> req) {
        List<SeatResponseDto> res = seatService.createSeatsInBulk(id, req);
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }


}
