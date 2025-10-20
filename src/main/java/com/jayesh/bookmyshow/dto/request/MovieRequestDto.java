package com.jayesh.bookmyshow.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Schema(name = "MovieRequestDto", description = "Used for creating or updating a movie with all necessary details.")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieRequestDto {

    @Schema(description = "Title of the movie.", example = "Inception")
    private String title;

    @Schema(description = "Brief description of the movie plot.", example = "A skilled thief enters people's dreams to steal secrets.")
    private String description;

    @Schema(description = "Genre of the movie.", example = "Sci-Fi")
    private String genre;

    @Schema(description = "Language of the movie.", example = "English")
    private String language;

    @Schema(description = "Duration of the movie in hours and minutes.", example = "2h 28m")
    private String duration;

    @Schema(description = "Release date of the movie.", example = "2010-07-16")
    private LocalDate releaseDate;
}
