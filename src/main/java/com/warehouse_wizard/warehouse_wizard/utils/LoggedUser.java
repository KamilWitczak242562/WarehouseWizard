package com.warehouse_wizard.warehouse_wizard.utils;

import com.warehouse_wizard.warehouse_wizard.model.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class LoggedUser {

    private User loggedUser;

    public User getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(User loggedUser) {
        this.loggedUser = loggedUser;
    }

    public void logOut() {
        this.loggedUser = null;
    }
}
