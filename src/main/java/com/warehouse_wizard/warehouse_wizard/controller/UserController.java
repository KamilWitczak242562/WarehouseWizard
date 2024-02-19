package com.warehouse_wizard.warehouse_wizard.controller;

import com.warehouse_wizard.warehouse_wizard.dto.UserDto;
import com.warehouse_wizard.warehouse_wizard.mapper.UserMapper;
import com.warehouse_wizard.warehouse_wizard.model.User;
import com.warehouse_wizard.warehouse_wizard.service.UserService;
import com.warehouse_wizard.warehouse_wizard.utils.RequiresLoggedInUser;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/allUsers")
    public ResponseEntity<Map<String, Integer>> getUsers() {
        Map<String, Integer> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PostMapping("/registration")
    public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto) {
        User user = userService.registerUser(UserMapper.userDTOToUser(userDto));
        return new ResponseEntity<>(UserMapper.userToUserDTO(user), HttpStatus.CREATED);
    }

    @GetMapping("/login")
    public ResponseEntity<UserDto> loginUser(@RequestBody UserDto userDto) {
        User user = userService.logInUser(UserMapper.userDTOToUser(userDto));
        return new ResponseEntity<>(UserMapper.userToUserDTO(user), HttpStatus.ACCEPTED);
    }

    @RequestMapping("/logout")
    @RequiresLoggedInUser
    public ResponseEntity<Boolean> logoutUser() {
        boolean logOut = userService.logOutUser();
        return ResponseEntity.ok(logOut);
    }
}
