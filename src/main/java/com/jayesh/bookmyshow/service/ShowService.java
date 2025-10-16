package com.jayesh.bookmyshow.service;

import com.jayesh.bookmyshow.dto.request.ShowRequestDto;
import com.jayesh.bookmyshow.dto.response.ShowResponseDto;
import com.jayesh.bookmyshow.entities.*;
import com.jayesh.bookmyshow.repo.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ShowService {
    private final ShowRepo  showRepo;
    private final MovieRepo movieRepo;
    private final ScreenRepo screenRepo;
    private final TheatreRepo theatreRepo;
    private final ShowSeatRepo showSeatRepo;
    private final SeatRepo seatRepo;

    public ShowResponseDto createShow(ShowRequestDto req){
        Show show = new Show();
        Show showToSave = mapReqToShow(show, req);
        Show temp= showRepo.save(showToSave);
        Set<Seat> seats = seatRepo.findAllSeatsByScreenId(req.getScreenId());
        List<ShowSeat> showSeats =
                seats.stream().map(
                        seat -> {
                            ShowSeat showSeat = new ShowSeat();
                            showSeat.setStatus("AVAILABLE");
                            showSeat.setPrice(req.getTicketPrice());
                            seat.addShowSeat(showSeat);
                            show.addShowSeat(showSeat);
                            return showSeat;
                        }
                ).toList();
        showSeatRepo.saveAll(showSeats);
        System.out.println("Saved Show Seats");
        return new ShowResponseDto(temp);
    }


    @Transactional
    public List<ShowResponseDto> createShowsInBulk(List<ShowRequestDto> requests) {

        // ----------- STEP 1: Saare "pizza bases" (Shows) banao aur save karo -----------
        // Pehle saare shows banaakar save karlo taaki unko ek unique ID mil jaaye.
        List<Show> showsToCreate = requests.stream().map(req -> {
            Show show = new Show();
            mapReqToShow(show, req); // Tumhara helper method bilkul sahi hai
            return show;
        }).toList();

        List<Show> savedShows = showRepo.saveAll(showsToCreate);
        System.out.println("Saved all " + savedShows.size() + " shows.");

        // ----------- STEP 2: Ab har saved show ke liye "slices" (ShowSeats) kaato -----------
        // Ek khali list banao jisme hum saare shows ke saare show-seats daalenge.
        List<ShowSeat> allShowSeatsToSave = new ArrayList<>();

        // Har saved show ke liye loop chalao.
        for (Show show : savedShows) {
            // Us show ke screen ki saari seats nikaalo.
            Set<Seat> seats = seatRepo.findAllSeatsByScreenId(show.getScreen().getId());

            // Har seat ke liye ek ShowSeat banao. Yeh logic tumhare single method jaisa hi hai.
            List<ShowSeat> showSeatsForThisShow = seats.stream().map(seat -> {
                ShowSeat showSeat = new ShowSeat();
                showSeat.setStatus("AVAILABLE");
                showSeat.setPrice(show.getTicketPrice()); // Show ka price use karo
                seat.addShowSeat(showSeat);
                show.addShowSeat(showSeat);
                return showSeat;
            }).toList();

            // Is show ke saare ShowSeats ko main list me add kar do.
            allShowSeatsToSave.addAll(showSeatsForThisShow);
        }


        // ----------- STEP 3: Saare "slices" (ShowSeats) ko ek saath save karo -----------
        // Aakhir me, saare shows ke saare ShowSeats ko ek hi database call me save kar do.
        showSeatRepo.saveAll(allShowSeatsToSave);
        System.out.println("Saved " + allShowSeatsToSave.size() + " show seats in total.");


        // ----------- STEP 4: Response bhej do -----------
        return savedShows.stream().map(ShowResponseDto::new).toList();
    }

    public ShowResponseDto updateShowDetails(Long id ,ShowRequestDto req){
        Show show = showRepo.findById(id).orElseThrow(()->new IllegalArgumentException("Show Not Found"));
        if(req.getShowDate() != null){
            show.setShowDate(req.getShowDate());
        }
        if (req.getShowTime() != null){
            show.setShowTime(req.getShowTime());
        }
        if (req.getTicketPrice()!=0){
            show.setTicketPrice(req.getTicketPrice());
        }
        showRepo.save(show);
        return new ShowResponseDto(show);
    }


    public List<ShowResponseDto> findAllShows(LocalDate showDate, LocalTime showTime, Double ticketPrice,Long movieId,Long screenId) {
         return showRepo
                 .findShowsByCriteria(showDate,showTime,ticketPrice,movieId,screenId)
                 .stream()
                 .map(ShowResponseDto::new)
                 .toList();
    }


    public void deleteAShow(Long id) {
        showRepo.deleteById(id);
    }



    public List<ShowResponseDto> findAllShowsForAMovie(Long id) {
        return showRepo.findAllShowsByMovieId(id).stream().map(ShowResponseDto::new).toList();
    }

    public List<ShowResponseDto> findAllShowsInATheatre(Long id,LocalDate date) {
        if(!theatreRepo.existsById(id)){
            throw new EntityNotFoundException("Theatre Not Found of id -> "+id);
        }
        return showRepo.findAllShowsByTheatreIdAndShowDate(id,date).stream().map(ShowResponseDto::new).toList();
    }



    //helpers

    private Show mapReqToShow(Show show, ShowRequestDto req) {
        Movie movie = movieRepo.findById(req.getMovieId()).orElseThrow(()->new IllegalArgumentException("Movie Not Found"));
        Screen screen = screenRepo.findById(req.getScreenId()).orElseThrow(()->new IllegalArgumentException("Screen Not Found"));
        show.setShowDate(req.getShowDate());
        show.setShowTime(req.getShowTime());
        show.setTicketPrice(req.getTicketPrice());
        movie.addShow(show);
        screen.addShow(show);
        return show;
    }

    public List<ShowResponseDto> findAllShowsOfAScreen(Long id,LocalDate showDate) {
        return showRepo.findALlShowsByScreenIdAndShowDate(id,showDate).stream().map(ShowResponseDto::new).toList();
    }
}
