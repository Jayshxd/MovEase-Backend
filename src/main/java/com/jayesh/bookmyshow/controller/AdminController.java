package com.jayesh.bookmyshow.controller;

import com.jayesh.bookmyshow.dto.request.RoleRequestController;
import com.jayesh.bookmyshow.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/assign")
    public ResponseEntity<?> assignRoleToUser(@RequestBody RoleRequestController roleRequestController) {
        String response = userService.assignRoleToUser(roleRequestController);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
