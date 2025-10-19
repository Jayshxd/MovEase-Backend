package com.jayesh.bookmyshow.service;

import com.jayesh.bookmyshow.dto.request.LoginRequestDto;
import com.jayesh.bookmyshow.dto.request.RoleRequestController;
import com.jayesh.bookmyshow.dto.request.UserRequestDto;
import com.jayesh.bookmyshow.dto.response.LoginResponseDto;
import com.jayesh.bookmyshow.dto.response.UserResponseDto;
import com.jayesh.bookmyshow.entities.Role;
import com.jayesh.bookmyshow.entities.User;
import com.jayesh.bookmyshow.jwt.JwtUtil;
import com.jayesh.bookmyshow.repo.RoleRepo;
import com.jayesh.bookmyshow.repo.UserRepo;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager; // The "Bouncer"
    private final JwtUtil jwtUtil; // The "Wristband Guy"

    public UserResponseDto registerUser(UserRequestDto value) {
        User newUser = mapDtoToUser(value);
        User savedUser = userRepo.save(newUser);
        return new UserResponseDto(savedUser,"User Registered Successfully");
    }

    @Transactional
    public List<UserResponseDto> registerUsersInBulk(List<UserRequestDto> value) {
        List<User> users = value.stream().map(
                this::mapDtoToUser
        ).toList();
        List<User> savedUsers = userRepo.saveAll(users);
        return savedUsers.stream().map(user -> new UserResponseDto(user,"User Registered Successfully")).collect(Collectors.toList());
    }

    public User mapDtoToUser(UserRequestDto value) {
        if(userRepo.findByUsername(value.getUsername()).isPresent()) {
            throw new IllegalStateException("Username is already Exists !!!"+value.getUsername());
        }

        Role role = roleRepo.findByName("user").orElseGet(
                ()-> roleRepo.save(new Role("user"))
        );
        User newUser = new User();
        newUser.setName(value.getName());
        newUser.setUsername(value.getUsername());
        newUser.setPassword(passwordEncoder.encode(value.getPassword()));
        newUser.setEmail(value.getEmail());
        newUser.setRoles(Set.of(role));
        return newUser;
    }

    public List<UserResponseDto> findAllUsers(String name, String username, String email) {
        List<User> users = userRepo.findByCriteria(name, username, email);
        return users.stream().map(user -> new UserResponseDto(user,"User Found Successfully")).collect(Collectors.toList());
    }

    public UserResponseDto updateUser(Long id, UserRequestDto value) {
        User user = userRepo.findById(id).orElseThrow(()->new EntityNotFoundException("User not found with id "+id));
        user.setName(value.getName());
        user.setUsername(value.getUsername());
        user.setEmail(value.getEmail());
        user.setPassword(passwordEncoder.encode(value.getPassword()));
        userRepo.save(user);
        return new UserResponseDto(user,"User Updated Successfully");
    }


    public UserResponseDto updateUserDetails(Long id, UserRequestDto value) {
        User user = userRepo.findById(id).orElseThrow(()->new EntityNotFoundException("User not found with id "+id));
        if(value.getName()!=null && !value.getName().isEmpty()) user.setName(value.getName());
        if (value.getUsername()!=null && !value.getUsername().isEmpty()) user.setUsername(value.getUsername());
        if(value.getEmail()!=null && !value.getEmail().isEmpty()) user.setEmail(value.getEmail());
        if(value.getPassword()!=null && !value.getPassword().isEmpty()) user.setPassword(value.getPassword());
        userRepo.save(user);
        return new UserResponseDto(user,"User Updated Successfully");
    }

    public void deleteUser(Long id) {
        userRepo.deleteById(id);
    }

    public UserResponseDto findUserById(Long id) {
        User user = userRepo.findById(id).orElseThrow(()->new EntityNotFoundException("User not found with id "+id));
        return new UserResponseDto(user,"User Found Successfully");
    }

    public String assignRoleToUser(RoleRequestController roleRequestController) {
        User user = userRepo.findById(roleRequestController.getUserId()).orElseThrow(()->new EntityNotFoundException("User not found with id "+roleRequestController.getUserId()));
        Set<Role> roles = user.getRoles();
        for(String roleName : roleRequestController.getRoles()) {
            Role role = roleRepo.findByName(roleName)
                    .orElseGet(
                            ()-> roleRepo.save(new Role(roleName))
                    );
            roles.add(role);
        }
        user.setRoles(roles);
        userRepo.save(user);
        return "Roles Assigned Successfully to -> " + user.getUsername();
    }

    public LoginResponseDto loginUser(LoginRequestDto request) {
        Authentication authentication = authenticationManager
                .authenticate(
                    new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                    )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String jwtToken = jwtUtil.generateToken(userDetails);
        return new LoginResponseDto(jwtToken, userDetails.getUsername(), "Login Successful");

    }
}
