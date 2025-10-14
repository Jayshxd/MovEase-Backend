package com.jayesh.bookmyshow.dto.response;

import com.jayesh.bookmyshow.entities.Movie;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieResponseDto {
    private Long id;
    private String title;
    private String duration;
    private String genre;
    private LocalDate releaseDate;
    private String language;

    public MovieResponseDto(Movie movie) {
        this.id = movie.getId();
        this.title = movie.getTitle();
        this.duration = movie.getDuration();
        this.genre = movie.getGenre();
        this.releaseDate = movie.getReleaseDate();
        this.language = movie.getLanguage();

    }
}
