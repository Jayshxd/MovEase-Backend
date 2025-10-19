package com.jayesh.bookmyshow.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//error ka response iss type ka dikhe
public class ErrorResponse {
    private Date timestamp;
    private int statusCode;
    private String error;
    private String message;
    private String path;

}
