package com.jayesh.bookmyshow.controller;

import com.jayesh.bookmyshow.dto.request.RoleRequestController;
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
@Tag(
        name = "Admin Management",
        description = "Endpoints for managing user roles and admin operations"
)

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;


    @Operation(
            summary = "Assign role to a user",
            description = "Allows an admin to assign a specific role (like USER or ADMIN) to an existing user account."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Role assigned successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - only admins can perform this action"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error while assigning role")
    })

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/assign")
    public ResponseEntity<?> assignRoleToUser(@RequestBody RoleRequestController roleRequestController) {
        String response = userService.assignRoleToUser(roleRequestController);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
