package com.jayesh.bookmyshow.service;

import com.jayesh.bookmyshow.dto.request.UserRequestDto;
import com.jayesh.bookmyshow.dto.response.UserResponseDto;
import com.jayesh.bookmyshow.entities.Role;
import com.jayesh.bookmyshow.entities.User;
import com.jayesh.bookmyshow.repo.RoleRepo;
import com.jayesh.bookmyshow.repo.UserRepo;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;


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

        Role role = roleRepo.findByName("ROLE_USER").orElseGet(
                ()-> roleRepo.save(new Role("ROLE_USER"))
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
}
