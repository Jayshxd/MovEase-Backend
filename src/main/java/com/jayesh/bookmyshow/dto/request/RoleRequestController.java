package com.jayesh.bookmyshow.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleRequestController {
    private Long userId;
    private Set<String> roles;
}
