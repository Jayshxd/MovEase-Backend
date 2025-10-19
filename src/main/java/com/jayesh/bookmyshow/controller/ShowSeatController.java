package com.jayesh.bookmyshow.controller;

import com.jayesh.bookmyshow.dto.request.ShowSeatRequestDto;
import com.jayesh.bookmyshow.dto.response.ShowSeatResponseDto;
import com.jayesh.bookmyshow.service.ShowSeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/showsSeats")
public class ShowSeatController {
    private final ShowSeatService showSeatService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ShowSeatResponseDto> post(@RequestBody ShowSeatRequestDto value) {
        ShowSeatResponseDto responseDto = showSeatService.createSeat(value);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

}
