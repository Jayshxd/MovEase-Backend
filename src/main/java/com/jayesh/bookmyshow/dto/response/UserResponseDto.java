package com.jayesh.bookmyshow.dto.response;

import com.jayesh.bookmyshow.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
    private Long id;
    private String name;
    private String message;

    public UserResponseDto(User user,String message) {
        this.id = user.getId();
        this.name = user.getName();
        this.message = message;
    }

}
