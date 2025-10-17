package com.jayesh.bookmyshow.service;

import com.jayesh.bookmyshow.dto.request.BookingRequestDto;
import com.jayesh.bookmyshow.dto.response.BookingResponseDto;
import com.jayesh.bookmyshow.entities.Booking;
import com.jayesh.bookmyshow.entities.Show;
import com.jayesh.bookmyshow.entities.ShowSeat;
import com.jayesh.bookmyshow.entities.User;
import com.jayesh.bookmyshow.repo.BookingRepo;
import com.jayesh.bookmyshow.repo.ShowRepo;
import com.jayesh.bookmyshow.repo.ShowSeatRepo;
import com.jayesh.bookmyshow.repo.UserRepo;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepo bookingRepo;
    private final UserRepo userRepo;
    private final ShowRepo showRepo;
    private final ShowSeatRepo showSeatRepo;
    private final static double MOVEASE_CONVINIENCE_FEE = 50;

    @Transactional
    public BookingResponseDto createBooking(BookingRequestDto bookingRequestDto) {
        User user = userRepo
                .findById(bookingRequestDto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with id -> "+bookingRequestDto.getUserId()));

        Show show = showRepo.findById(bookingRequestDto.getShowId())
                .orElseThrow(() -> new EntityNotFoundException("Show not found with id -> "+bookingRequestDto.getShowId()));

        List<Long> usersSeatsIds = bookingRequestDto.getShowSeatIds();
        List<ShowSeat> usersSeats = showSeatRepo.findAllById(usersSeatsIds);

        if(usersSeatsIds.size()!=usersSeats.size()){
            throw new EntityNotFoundException("One or more requested seats were not found in the database.");
        }

        double totalAmount = 0;
        for(ShowSeat showSeat : usersSeats) {
            if(!showSeat.getStatus().equals("AVAILABLE")) {
                throw new EntityNotFoundException(showSeat.getId()+" This Seat is Already Booked for this show "+showSeat.getShow().getId());
            }
            totalAmount = showSeat.getPrice()+totalAmount;
        }
        double gst = (totalAmount*18)/100;
        Booking booking = new Booking();
        booking.setBookingTime(LocalTime.now());
        booking.setTotalAmount(totalAmount+MOVEASE_CONVINIENCE_FEE+gst);
        user.addBooking(booking);
        show.addBooking(booking);
        Booking savedBooking = bookingRepo.save(booking);
        for(ShowSeat showSeat : usersSeats){
            showSeat.setBooking(savedBooking);
            showSeat.setStatus("BOOKED");
        }
        showSeatRepo.saveAll(usersSeats);
        return new BookingResponseDto(savedBooking);
    }



}
