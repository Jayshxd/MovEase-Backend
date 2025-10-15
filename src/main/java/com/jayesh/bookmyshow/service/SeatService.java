package com.jayesh.bookmyshow.service;

import com.jayesh.bookmyshow.dto.request.SeatRequestDto;
import com.jayesh.bookmyshow.dto.response.SeatResponseDto;
import com.jayesh.bookmyshow.entities.Screen;
import com.jayesh.bookmyshow.entities.Seat;
import com.jayesh.bookmyshow.repo.ScreenRepo;
import com.jayesh.bookmyshow.repo.SeatRepo;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SeatService {
    private final SeatRepo seatRepo;
    private final ScreenRepo screenRepo;
    public SeatResponseDto createSeat(Long id, SeatRequestDto seatRequestDto) {
        Screen screen = screenRepo.findById(id).orElseThrow(()->new EntityNotFoundException("Screen not found"));
        Seat seat = new Seat();
        copyPasteWork(seat,seatRequestDto);
        screen.addSeat(seat);
        Seat res =  seatRepo.save(seat);
        return new SeatResponseDto(res);
    }

    @Transactional
    public List<SeatResponseDto> createSeatsInBulk(Long id,List<SeatRequestDto> seatRequestDtos) {
        Screen screen = screenRepo.findById(id).orElseThrow(()->new EntityNotFoundException("Screen not found"));
        List<Seat> res = seatRequestDtos.stream().map(
                seat ->{
                    Seat newSeat = new Seat();
                    copyPasteWork(newSeat,seat);
                    screen.addSeat(newSeat);
                    return newSeat;
                }
        ).toList();
        List<Seat> res2 = seatRepo.saveAll(res);
        return res2.stream().map(SeatResponseDto::new).collect(Collectors.toList());
    }


    public List<SeatResponseDto> findAllSeatsForAScreen(Long id, String seatNumber, String seatType){
        if (!screenRepo.existsById(id)) {
            throw new EntityNotFoundException("Screen not found with id: " + id);
        }
        List<Seat> seats = seatRepo.findByScreenIdAndCriteria(id, seatNumber, seatType);
        return seats.stream().map(SeatResponseDto::new).collect(Collectors.toList());
    }


    public List<SeatResponseDto> findSeatsInSystem(String seatNumber, String seatType) {
        List<Seat> seats = seatRepo.findByCriteria(seatNumber, seatType);
        return seats.stream().map(SeatResponseDto::new).collect(Collectors.toList());
    }



    public SeatResponseDto updateSeat(Long id, SeatRequestDto seatRequestDto) {
        Seat seat = seatRepo.findById(id).orElseThrow(()->new EntityNotFoundException("Seat not found"));
        copyPasteWork(seat,seatRequestDto);
        seatRepo.save(seat);
        return new SeatResponseDto(seat);
    }

    public SeatResponseDto updateSeatDetails(Long id, SeatRequestDto seatRequestDto) {
        Seat seat = seatRepo.findById(id).orElseThrow(()->new EntityNotFoundException("Seat not found"));
        if(seatRequestDto.getSeatNumber()!=null && !seatRequestDto.getSeatNumber().isEmpty()){
            seat.setSeatNumber(seatRequestDto.getSeatNumber());
        }
        if(seatRequestDto.getSeatType()!=null && !seatRequestDto.getSeatType().isEmpty()){
            seat.setSeatType(seatRequestDto.getSeatType());
        }
        Seat temp = seatRepo.save(seat);
        return new SeatResponseDto(temp);
    }

    public void deleteSeat(Long id) {
        seatRepo.deleteById(id);
    }





    //helper methods
    private void copyPasteWork(Seat seat, SeatRequestDto seatRequestDto) {
        seat.setSeatNumber(seatRequestDto.getSeatNumber());
        seat.setSeatType(seatRequestDto.getSeatType());
    }

    public List<SeatResponseDto> mapDtoToEnt(Collection<Seat> seats,String seatNumber, String seatType) {
        if(seatNumber!=null && !seatNumber.isEmpty()){
            seats=seats.stream().filter(seat -> seat.getSeatNumber().toLowerCase().contains(seatNumber.toLowerCase())).toList();
        }
        if(seatType!=null && !seatType.isEmpty()){
            seats=seats.stream().filter(seat -> seat.getSeatType().toLowerCase().contains(seatType.toLowerCase())).collect(Collectors.toList());
        }
        return seats.stream().map(SeatResponseDto::new).collect(Collectors.toList());
    }
}
