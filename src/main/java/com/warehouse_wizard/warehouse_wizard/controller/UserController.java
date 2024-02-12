package com.warehouse_wizard.warehouse_wizard.controller;

import com.warehouse_wizard.warehouse_wizard.model.User;
import com.warehouse_wizard.warehouse_wizard.service.UserService;
import com.warehouse_wizard.warehouse_wizard.utils.RequiresLoggedInUser;
import lombok.AllArgsConstructor;
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
    public @ResponseBody User getUserById(@PathVariable int id) {
        return userService.getUserById(id);
    }

    @GetMapping("/allUsers")
    @RequiresLoggedInUser
    public @ResponseBody Map<String, Integer> getUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/getUserByEmail")
    @RequiresLoggedInUser
    public @ResponseBody User getUserByEmail(@RequestParam String email) {
        return userService.getUserByEmail(email);
    }

    @PostMapping("/registration")
    public @ResponseBody User registerUser(@RequestBody Map<String, String> credentials) {
        return userService.registerUser(credentials.get("email"), credentials.get("password"));
    }

    @GetMapping("/login")
    public @ResponseBody User loginUser(@RequestBody Map<String, String> credentials) {
        return userService.logInUser(credentials.get("email"), credentials.get("password"));
    }

    @RequestMapping("/logout")
    @RequiresLoggedInUser
    public @ResponseBody boolean logoutUser() {
        return userService.logOutUser();
    }
}
