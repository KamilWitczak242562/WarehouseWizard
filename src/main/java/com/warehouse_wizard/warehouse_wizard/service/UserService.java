package com.warehouse_wizard.warehouse_wizard.service;

import com.mysql.cj.exceptions.WrongArgumentException;
import com.warehouse_wizard.warehouse_wizard.model.User;
import com.warehouse_wizard.warehouse_wizard.repository.UserRepository;
import com.warehouse_wizard.warehouse_wizard.utils.LoggedUser;
import lombok.AllArgsConstructor;
import ognl.MethodFailedException;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;
    private LoggedUser loggedUser;

    public User registerUser(String email, String password) {
        if (userRepository.findByEmail(email) != null) {
            throw new WrongArgumentException("User with this email already exists!");
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
        userRepository.save(user);
        loggedUser.setLoggedUser(user);
        return user;
    }

    public User logInUser(String email, String password) {
        if (userRepository.findByEmail(email) == null) {
            throw new WrongArgumentException("User with this email not existing!");
        }
        if (loggedUser.getLoggedUser() != null) {
            throw new WrongArgumentException("User already logged in!");
        }
        User user = userRepository.findByEmail(email);
        if (BCrypt.checkpw(password, user.getPassword())) {
            loggedUser.setLoggedUser(user);
            return user;
        } else {
            throw new WrongArgumentException("Wrong password!");
        }
    }

    public User getUserById(int id) {
        return userRepository.findById(id).orElseThrow();
    }

    public Map<String, Integer> getAllUsers() {
        List<User> users = userRepository.findAll();
        Map<String, Integer> emails = new HashMap<>();
        for (User user : users) {
            emails.put(user.getEmail(), user.getId());
        }
        return emails;
    }

    public User getUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (userRepository.findByEmail(email) != null) {
            return user;
        } else {
            throw new WrongArgumentException("User with this email not existing!");
        }
    }

    public boolean logOutUser() {
        if (loggedUser.getLoggedUser() == null) {
            throw new WrongArgumentException("User is not logged in");
        }
        loggedUser.logOut();
        if (loggedUser.getLoggedUser() == null) {
            return true;
        } else {
            return false;
        }
    }
}
