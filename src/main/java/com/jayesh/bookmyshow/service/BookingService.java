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
import java.util.Iterator;
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
        for(ShowSeat showSeat : usersSeats){
            booking.addShowSeat(showSeat);
            showSeat.setStatus("BOOKED");
        }
        Booking savedBooking = bookingRepo.save(booking);
        showSeatRepo.saveAll(usersSeats);
        return new BookingResponseDto(savedBooking);
    }


    @Transactional
    public String cancelBooking(Long id){
        Booking booking = bookingRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Booking not found with id -> "+id));
        //booking ka removeShowSeat
        User user = booking.getUser();
        if(user==null){
            throw new RuntimeException("USER IS NULL >> IN BOOKINGS TABLE");
        }
        Show show = booking.getShow();
        if(show==null){
            throw new RuntimeException("SHOW IS NULL >> IN BOOKINGS TABLE");
        }

        Iterator<ShowSeat> iterator = booking.getShowSeats().iterator();
        while (iterator.hasNext()){
            ShowSeat showSeat = iterator.next();
            showSeat.setStatus("AVAILABLE");
            showSeat.setBooking(null);
            iterator.remove(); // yeh safe hai, ye hi karna chahiye
        }
        showSeatRepo.saveAll(booking.getShowSeats());
        booking.setBookingStatus("CANCELLED");
        return "Booking has been cancelled";
    }


}
