package com.jayesh.bookmyshow.controller;

import com.jayesh.bookmyshow.dto.request.LoginRequestDto;
import com.jayesh.bookmyshow.dto.request.UserRequestDto;
import com.jayesh.bookmyshow.dto.response.LoginResponseDto;
import com.jayesh.bookmyshow.dto.response.UserResponseDto;
import com.jayesh.bookmyshow.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String email
    ) {
        List<UserResponseDto> response = userService.findAllUsers(name,username,email);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('user','admin')")
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id) {
        UserResponseDto responseDto = userService.findUserById(id);
        return ResponseEntity.ok(responseDto);
    }


    @PostMapping("auth/register")
    public ResponseEntity<UserResponseDto> registerUser(@RequestBody UserRequestDto value) {
        UserResponseDto userResponseDto = userService.registerUser(value);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponseDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("auth/register/bulk")
    public ResponseEntity<List<UserResponseDto>> registerUsersInBulk(@RequestBody List<UserRequestDto> value) {
        List<UserResponseDto> userResponseDto = userService.registerUsersInBulk(value);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponseDto);
    }


    @PostMapping("auth/login")
    public ResponseEntity<LoginResponseDto> loginUser(@RequestBody LoginRequestDto request) {
        LoginResponseDto response = userService.loginUser(request);
        return ResponseEntity.ok(response);
    }



    @PreAuthorize("hasAnyRole('user','admin')")
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable Long id, @RequestBody UserRequestDto value) {
        UserResponseDto user = userService.updateUser(id,value);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @PreAuthorize("hasAnyRole('user','admin')")
    @PatchMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUserDetails(@PathVariable Long id, @RequestBody UserRequestDto value) {
        UserResponseDto user = userService.updateUserDetails(id,value);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @PreAuthorize("hasAnyRole('user','admin')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
