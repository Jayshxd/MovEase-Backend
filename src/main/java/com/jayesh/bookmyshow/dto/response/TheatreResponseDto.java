package com.jayesh.bookmyshow.dto.response;

import com.jayesh.bookmyshow.entities.Screen;
import com.jayesh.bookmyshow.entities.Theatre;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
@Schema(
        name = "TheatreResponseDto",
        description = "Response object containing details of a theatre including its name, city, and associated screens."
)

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TheatreResponseDto {
    @Schema(description = "Unique identifier of the theatre.", example = "12")
    private Long id;

    @Schema(description = "Name of the theatre.", example = "PVR Phoenix Market City")
    private String name;

    @Schema(description = "City where the theatre is located.", example = "Pune")
    private String city;

    @Schema(description = "Set of screen names available in this theatre.", example = "[\"Screen 1\", \"Screen 2\", \"IMAX Hall\"]")
    private Set<String> screens;


    public TheatreResponseDto(Theatre theatre) {
        this.id = theatre.getId();
        this.name = theatre.getName();
        this.city = theatre.getCity();
        this.screens = theatre.getScreens().stream().map(Screen::getName).collect(Collectors.toSet());
    }
}
