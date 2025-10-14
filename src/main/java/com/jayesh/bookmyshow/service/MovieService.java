package com.jayesh.bookmyshow.service;

import com.jayesh.bookmyshow.dto.request.MovieRequestDto;
import com.jayesh.bookmyshow.dto.response.MovieResponseDto;
import com.jayesh.bookmyshow.entities.Movie;
import com.jayesh.bookmyshow.repo.MovieRepo;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepo movieRepo;


    public List<MovieResponseDto> findMovies(String title, String description, String genre, String language,String duration, LocalDate releaseDate
    ) {
        List<Movie> movies = movieRepo.findAll();
        if(title!=null && !title.isEmpty()){

            movies = movies.stream().filter(movie -> movie.getTitle().toLowerCase().contains(title.toLowerCase())).toList();

        }
        if(description!=null  && !description.isEmpty()){

            movies=movies.stream().filter(movie -> movie.getDescription().toLowerCase().contains(description.toLowerCase())).toList();

        }
        if(genre!=null && !genre.isEmpty()){

            movies=movies.stream().filter(movie -> movie.getGenre().toLowerCase().contains(genre.toLowerCase())).toList();

        }
        if(language!=null && !language.isEmpty()){

            movies=movies.stream().filter(movie -> movie.getLanguage().toLowerCase().contains(language.toLowerCase())).toList();

        }
        if(releaseDate!=null){

            movies=movies.stream().filter(movie -> movie.getReleaseDate().isAfter(releaseDate)).toList();

        }if(duration!=null && !duration.isEmpty()) {

            movies=movies.stream().filter(movie -> movie.getDuration().contains(duration.toLowerCase())).toList();

        }
        return movies.stream().map(MovieResponseDto::new).collect(Collectors.toList());
    }


    public MovieResponseDto createMovie(MovieRequestDto req) {
        Movie newMovie = new Movie();
        newMovie.setTitle(req.getTitle());
        newMovie.setDuration(req.getDuration());
        newMovie.setReleaseDate(req.getReleaseDate());
        newMovie.setDescription(req.getDescription());
        newMovie.setGenre(req.getGenre());
        newMovie.setLanguage(req.getLanguage());
        movieRepo.save(newMovie);
        return new MovieResponseDto(newMovie);
    }

    @Transactional
    public List<MovieResponseDto> createMoviesInBulk(List<MovieRequestDto> req) {
        List<Movie> moviesToSave = req.stream().map(
                dto->{
                    Movie newMovie = new Movie();
                    newMovie.setTitle(dto.getTitle());
                    newMovie.setDuration(dto.getDuration());
                    newMovie.setReleaseDate(dto.getReleaseDate());
                    newMovie.setDescription(dto.getDescription());
                    newMovie.setGenre(dto.getGenre());
                    newMovie.setLanguage(dto.getLanguage());
                    return newMovie;
                }
        ).toList();
        List<Movie> savedMovies = movieRepo.saveAll(moviesToSave);
        return savedMovies.stream().map(MovieResponseDto::new).collect(Collectors.toList());
    }


    public MovieResponseDto updateByPut(Long id, MovieRequestDto req) {
        Movie movie = movieRepo.findById(id).orElseThrow(()-> new EntityNotFoundException("Movie Not Found"));
        movie.setTitle(req.getTitle());
        movie.setDuration(req.getDuration());
        movie.setDescription(req.getDescription());
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
