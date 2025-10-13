package com.jayesh.bookmyshow.dto.response;

import com.jayesh.bookmyshow.entities.Movie;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieResponseDto {
    private Long id;
    private String title;
    private String duration;

    public MovieResponseDto(Movie movie) {
        this.id = movie.getId();
        this.title = movie.getTitle();
        this.duration = movie.getDuration();

    }
}
