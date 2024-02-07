package com.warehouse_wizard.warehouse_wizard.controller;

import com.mysql.cj.log.Log;
import com.warehouse_wizard.warehouse_wizard.entity.User;
import com.warehouse_wizard.warehouse_wizard.repository.UserRepository;
import com.warehouse_wizard.warehouse_wizard.utils.LoggedUser;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository repository;

    private LoggedUser loggedUser;

    @GetMapping("/get")
    public @ResponseBody ResponseEntity<User> getUserById(@RequestParam int id) {
        Optional<User> userOptional = repository.findById(id);

        return userOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/registration")
    public @ResponseBody ResponseEntity<String> registerUser(@RequestBody Map<String, String> credentials) {
        if (repository.findByEmail(credentials.get("email")) != null) {
            return ResponseEntity.badRequest().build();
        }
        User user = new User();
        user.setEmail(credentials.get("email"));
        user.setPassword(BCrypt.hashpw(credentials.get("password"), BCrypt.gensalt()));
        repository.save(user);
        this.loggedUser = LoggedUser.getLoggedUser(user);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/login")
    public @ResponseBody ResponseEntity<String> loginUser(@RequestBody Map<String, String> credentials) {
        if (repository.findByEmail(credentials.get("email")) == null) {
            return ResponseEntity.badRequest().build();
        }
        User user = repository.findByEmail(credentials.get("email"));
        if (BCrypt.checkpw(credentials.get("password"), user.getPassword())) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @RequestMapping("/logout")
    public void logoutUser() {
        this.loggedUser.logOut();
    }
}
