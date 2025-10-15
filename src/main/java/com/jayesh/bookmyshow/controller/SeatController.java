package com.jayesh.bookmyshow.controller;

import com.jayesh.bookmyshow.dto.request.SeatRequestDto;
import com.jayesh.bookmyshow.dto.response.SeatResponseDto;
import com.jayesh.bookmyshow.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RequiredArgsConstructor
@RestController
@RequestMapping("/seats")
public class SeatController {

    private final SeatService seatService;

    @GetMapping
    public ResponseEntity<List<SeatResponseDto>> findSeats(
            @RequestParam(required = false) String seatNumber,
            @RequestParam(required = false) String seatType
    ){
        List<SeatResponseDto> res = seatService.findSeatsInSystem(seatNumber, seatType);
        return ResponseEntity.ok(res);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SeatResponseDto> updateSeat(@PathVariable Long id,@RequestBody SeatRequestDto seatRequestDto){
        SeatResponseDto res = seatService.updateSeat(id,seatRequestDto);
        return ResponseEntity.ok(res);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<SeatResponseDto> updateSeatDetails(@PathVariable Long id, @RequestBody SeatRequestDto seatRequestDto){
        SeatResponseDto res = seatService.updateSeatDetails(id,seatRequestDto);
        return ResponseEntity.ok(res);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        seatService.deleteSeat(id);
        return ResponseEntity.noContent().build();
    }

}
