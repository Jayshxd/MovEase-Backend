package com.jayesh.bookmyshow.dto.response;

import com.jayesh.bookmyshow.entities.Movie;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Response DTO representing a movie")
public class MovieResponseDto {

    @Schema(description = "Unique ID of the movie", example = "11")
    private Long id;

    @Schema(description = "Title of the movie", example = "Inception")
    private String title;

    @Schema(description = "Duration of the movie", example = "2h 28m")
    private String duration;

    @Schema(description = "Genre of the movie", example = "Sci-Fi")
    private String genre;

    @Schema(description = "Release date of the movie", example = "2010-07-16")
    private LocalDate releaseDate;

    @Schema(description = "Language of the movie", example = "English")
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
