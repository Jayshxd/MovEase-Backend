package com.jayesh.bookmyshow.service;

import com.jayesh.bookmyshow.dto.request.MovieRequestDto;
import com.jayesh.bookmyshow.dto.response.MovieResponseDto;
import com.jayesh.bookmyshow.entities.Movie;
import com.jayesh.bookmyshow.repo.MovieRepo;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepo movieRepo;


    public List<MovieResponseDto> findMovies() {
        List<Movie> movies = movieRepo.findAll();
        List<MovieResponseDto> res = new ArrayList<>();
        for (Movie movie : movies) {
            res.add(new MovieResponseDto(movie));
        }
        return res;
    }


    public MovieResponseDto createMovie(MovieRequestDto req) {
        Movie newMovie = new Movie();
        newMovie.setTitle(req.getTitle());
        newMovie.setDuration(req.getDuration());
        newMovie.setReleaseDate(req.getReleaseDate());
        newMovie.setDuration(req.getDuration());
        newMovie.setGenre(req.getGenre());
        newMovie.setLanguage(req.getLanguage());
        movieRepo.save(newMovie);
        return new MovieResponseDto(newMovie);
    }

    @Transactional
    public List<MovieResponseDto> createMoviesInBulk(List<MovieRequestDto> req) {
        List<MovieResponseDto> res = new ArrayList<>();
        for (MovieRequestDto movieRequestDto : req) {
            MovieResponseDto resp = createMovie(movieRequestDto);
            res.add(resp);
        }
        return res;
    }


    public MovieResponseDto updateByPut(Long id, MovieRequestDto req) {
        Movie movie = movieRepo.findById(id).orElseThrow(()-> new EntityNotFoundException("Movie Not Found"));
        movie.setTitle(req.getTitle());
        movie.setDuration(req.getDuration());
        movie.setDescription(req.getDuration());
        movie.setGenre(req.getGenre());
        movie.setLanguage(req.getLanguage());
        movie.setReleaseDate(req.getReleaseDate());
        movieRepo.save(movie);
        return new MovieResponseDto(movie);
    }


    public void deleteMovie(Long id) {
        movieRepo.deleteById(id);
    }

    public MovieResponseDto getMovieById(Long id) {
        Movie movie = movieRepo.findById(id).orElseThrow(()-> new EntityNotFoundException("Movie Not Found"));
        return new MovieResponseDto(movie);
    }
}
