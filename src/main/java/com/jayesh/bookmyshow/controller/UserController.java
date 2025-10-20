package com.jayesh.bookmyshow.controller;

import com.jayesh.bookmyshow.dto.request.LoginRequestDto;
import com.jayesh.bookmyshow.dto.request.UserRequestDto;
import com.jayesh.bookmyshow.dto.response.LoginResponseDto;
import com.jayesh.bookmyshow.dto.response.UserResponseDto;
import com.jayesh.bookmyshow.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "User Management APIs",
        description = "Handles user registration, authentication, retrieval, updates, and deletion. Accessible roles are mentioned per endpoint."
)

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Operation(
            summary = "Retrieve users with optional filters",
            description = "Fetch all users or filter them by name, username, or email. Only accessible by ADMIN role."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Users retrieved successfully"),
            @ApiResponse(responseCode = "403", description = "Access denied - only admins can view all users"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })

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


    @Operation(
            summary = "Get user by ID",
            description = "Retrieve a single user's details using their unique ID. Accessible by both USER and ADMIN roles."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User details fetched successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "403", description = "Unauthorized access")
    })

    @PreAuthorize("hasAnyRole('user','admin')")
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id) {
        UserResponseDto responseDto = userService.findUserById(id);
        return ResponseEntity.ok(responseDto);
    }



    @Operation(
            summary = "Register a new user",
            description = "Allows a new user to create an account by providing basic information."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "User registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input or missing required fields")
    })
    @PostMapping("auth/register")
    public ResponseEntity<UserResponseDto> registerUser(@RequestBody UserRequestDto value) {
        UserResponseDto userResponseDto = userService.registerUser(value);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponseDto);
    }


    @Operation(
            summary = "Register multiple users (bulk)",
            description = "Allows ADMIN to register multiple users in one request."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Users registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid user data in bulk request"),
            @ApiResponse(responseCode = "403", description = "Access denied - only admins can perform bulk registration")
    })

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("auth/register/bulk")
    public ResponseEntity<List<UserResponseDto>> registerUsersInBulk(@RequestBody List<UserRequestDto> value) {
        List<UserResponseDto> userResponseDto = userService.registerUsersInBulk(value);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponseDto);
    }


    @Operation(
            summary = "User login",
            description = "Authenticate a user using email/username and password. Returns access token on success."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login successful"),
            @ApiResponse(responseCode = "401", description = "Invalid credentials or unauthorized access")
    })

    @PostMapping("auth/login")
    public ResponseEntity<LoginResponseDto> loginUser(@RequestBody LoginRequestDto request) {
        LoginResponseDto response = userService.loginUser(request);
        return ResponseEntity.ok(response);
    }



    @Operation(
            summary = "Update user details (full)",
            description = "Completely update a user’s information. Requires authentication and proper role access."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "403", description = "Unauthorized access")
    })
    @PreAuthorize("hasAnyRole('user','admin')")
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable Long id, @RequestBody UserRequestDto value) {
        UserResponseDto user = userService.updateUser(id,value);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @Operation(
            summary = "Partially update user details",
            description = "Update specific fields of a user profile without sending the full object."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User details updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid update request"),
            @ApiResponse(responseCode = "403", description = "Unauthorized access")
    })
    @PreAuthorize("hasAnyRole('user','admin')")
    @PatchMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUserDetails(@PathVariable Long id, @RequestBody UserRequestDto value) {
        UserResponseDto user = userService.updateUserDetails(id,value);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @Operation(
            summary = "Delete user account",
            description = "Delete a user by ID. Accessible by USER (self) or ADMIN."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "User deleted successfully"),
            @ApiResponse(responseCode = "403", description = "Unauthorized to delete this user"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })

    @PreAuthorize("hasAnyRole('user','admin')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
