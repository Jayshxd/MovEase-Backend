package com.jayesh.bookmyshow.service;

import com.jayesh.bookmyshow.dto.request.ShowRequestDto;
import com.jayesh.bookmyshow.dto.response.ShowResponseDto;
import com.jayesh.bookmyshow.entities.Movie;
import com.jayesh.bookmyshow.entities.Screen;
import com.jayesh.bookmyshow.entities.Show;
import com.jayesh.bookmyshow.repo.MovieRepo;
import com.jayesh.bookmyshow.repo.ScreenRepo;
import com.jayesh.bookmyshow.repo.ShowRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ShowService {
    private final ShowRepo  showRepo;
    private final MovieRepo movieRepo;
    private final ScreenRepo screenRepo;

    public ShowResponseDto createShow(ShowRequestDto req){
        Show show = new Show();
        Show showToSave = mapReqToShow(show, req);
        Show temp= showRepo.save(showToSave);
        return new ShowResponseDto(temp);
    }


    @Transactional
    public List<ShowResponseDto> createShowsInBulk(List<ShowRequestDto> request) {
        List<Show> shows = request.stream().map(
                req ->{
                    Show show = new Show();
                    mapReqToShow(show, req);
                    return show;
                }
        ).toList();
        List<Show> savedShows =showRepo.saveAll(shows);
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
}
