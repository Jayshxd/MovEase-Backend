package com.jayesh.bookmyshow.dto.response;

import com.jayesh.bookmyshow.entities.Screen;
import com.jayesh.bookmyshow.entities.Theatre;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TheatreResponseDto {
    private String name;
    private String city;
    private Set<String> screens;
    private Long id;

    public TheatreResponseDto(Theatre theatre) {
        this.id = theatre.getId();
        this.name = theatre.getName();
        this.city = theatre.getCity();
        this.screens = theatre.getScreens().stream().map(Screen::getName).collect(Collectors.toSet());
    }
}
