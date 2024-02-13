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

    private final UserRepository userRepository;
    private final LoggedUser loggedUser;

    public User registerUser(User user) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new WrongArgumentException("User with this email already exists!");
        }
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        userRepository.save(user);
        loggedUser.setLoggedUser(user);
        return user;
    }

    public User logInUser(User user) {
        if (userRepository.findByEmail(user.getEmail()) == null) {
            throw new WrongArgumentException("User with this email not existing!");
        }
        if (loggedUser.getLoggedUser() != null) {
            throw new WrongArgumentException("User already logged in!");
        }
        String password = userRepository.findByEmail(user.getEmail()).getPassword();
        if (BCrypt.checkpw(user.getPassword(), password)) {
            loggedUser.setLoggedUser(user);
            return user;
        } else {
            throw new WrongArgumentException("Wrong password!");
        }
    }

    public Map<String, Integer> getAllUsers() {
        List<User> users = userRepository.findAll();
        Map<String, Integer> emails = new HashMap<>();
        for (User user : users) {
            emails.put(user.getEmail(), user.getId());
        }
        return emails;
    }

    public boolean logOutUser() {
        if (loggedUser.getLoggedUser() == null) {
            throw new WrongArgumentException("User is not logged in");
        }
        loggedUser.logOut();
        return loggedUser.getLoggedUser() == null;
    }
}
