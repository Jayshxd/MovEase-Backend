package com.jayesh.bookmyshow.controller;

import com.jayesh.bookmyshow.dto.request.UserRequestDto;
import com.jayesh.bookmyshow.dto.response.UserResponseDto;
import com.jayesh.bookmyshow.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String email
    ) {
        List<UserResponseDto> response = userService.findAllUsers(name,username,email);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id) {
        UserResponseDto responseDto = userService.findUserByid(id);
        return ResponseEntity.ok(responseDto);
    }


    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> registerUser(@RequestBody UserRequestDto value) {
        UserResponseDto userResponseDto = userService.registerUser(value);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponseDto);
    }

    @PostMapping("/register/bulk")
    public ResponseEntity<List<UserResponseDto>> registerUsersInBulk(@RequestBody List<UserRequestDto> value) {
        List<UserResponseDto> userResponseDto = userService.registerUsersInBulk(value);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable Long id, @RequestBody UserRequestDto value) {
        UserResponseDto user = userService.updateUser(id,value);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUserDetails(@PathVariable Long id, @RequestBody UserRequestDto value) {
        UserResponseDto user = userService.updateUserDetails(id,value);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
