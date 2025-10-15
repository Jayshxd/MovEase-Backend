package com.jayesh.bookmyshow.dto.request;

import com.jayesh.bookmyshow.entities.Screen;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TheatreRequestDto {
    private String name;
    private String city;
    private String address;
}
