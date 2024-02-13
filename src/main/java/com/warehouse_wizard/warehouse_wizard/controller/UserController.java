package com.warehouse_wizard.warehouse_wizard.controller;

import com.warehouse_wizard.warehouse_wizard.model.User;
import com.warehouse_wizard.warehouse_wizard.service.UserService;
import com.warehouse_wizard.warehouse_wizard.utils.RequiresLoggedInUser;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


/***
 * Update response body to match web http responses
 */
@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    @RequiresLoggedInUser
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/allUsers")
    @RequiresLoggedInUser
    public ResponseEntity<Map<String, Integer>> getUsers() {
        Map<String, Integer> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/getUserByEmail")
    @RequiresLoggedInUser
    public ResponseEntity<User> getUserByEmail(@RequestParam String email) {
        User user = userService.getUserByEmail(email);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/registration")
    public ResponseEntity<User> registerUser(@RequestBody Map<String, String> credentials) {
        User user = userService.registerUser(credentials.get("email"), credentials.get("password"));
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @GetMapping("/login")
    public ResponseEntity<User> loginUser(@RequestBody Map<String, String> credentials) {
        User user = userService.logInUser(credentials.get("email"), credentials.get("password"));
        return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
    }

    @RequestMapping("/logout")
    @RequiresLoggedInUser
    public ResponseEntity<Boolean> logoutUser() {
        boolean logOut = userService.logOutUser();
        return ResponseEntity.ok(logOut);
    }
}
