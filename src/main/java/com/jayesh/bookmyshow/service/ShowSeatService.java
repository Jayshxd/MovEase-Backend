package com.jayesh.bookmyshow.service;

import com.jayesh.bookmyshow.dto.request.ShowSeatRequestDto;
import com.jayesh.bookmyshow.dto.response.ShowSeatResponseDto;
import com.jayesh.bookmyshow.entities.Seat;
import com.jayesh.bookmyshow.entities.Show;
import com.jayesh.bookmyshow.entities.ShowSeat;
import com.jayesh.bookmyshow.repo.SeatRepo;
import com.jayesh.bookmyshow.repo.ShowRepo;
import com.jayesh.bookmyshow.repo.ShowSeatRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShowSeatService {
    private final ShowSeatRepo showSeatRepo;
    private final SeatRepo seatRepo;
    private final ShowRepo showRepo;

    public ShowSeatResponseDto createSeat(ShowSeatRequestDto showSeatRequestDto) {
        Seat seat = seatRepo.findById(showSeatRequestDto.getSeatId()).orElseThrow(() -> new EntityNotFoundException("Seat not found"));
        Show show = showRepo.findById(showSeatRequestDto.getShowId()).orElseThrow(() -> new EntityNotFoundException("Show not found"));
        ShowSeat showSeat = new ShowSeat();
        showSeat.setStatus(showSeatRequestDto.getStatus());
        showSeat.setPrice(showSeatRequestDto.getPrice());
        seat.addShowSeat(showSeat);
        show.addShowSeat(showSeat);
        ShowSeat temp = showSeatRepo.save(showSeat);
        return new ShowSeatResponseDto(temp);
    }

}
