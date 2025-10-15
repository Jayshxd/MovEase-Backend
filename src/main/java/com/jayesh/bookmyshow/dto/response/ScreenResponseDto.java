package com.jayesh.bookmyshow.dto.response;

import com.jayesh.bookmyshow.entities.Screen;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ScreenResponseDto {
    private Long id;
    private String name;
    private int totalSeats;
    private Long theatreId;
    public ScreenResponseDto(Screen screen) {
        this.id = screen.getId();
        this.name = screen.getName();
        this.totalSeats = screen.getTotalSeats();
        this.theatreId = screen.getTheatre().getId();
    }
}
