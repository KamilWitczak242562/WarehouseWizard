package com.warehouse_wizard.warehouse_wizard.utils;

import com.warehouse_wizard.warehouse_wizard.model.User;
import lombok.Getter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Getter
@Component
public class LoggedUser {

    private User loggedUser;

    public void setLoggedUser(User loggedUser) {
        this.loggedUser = loggedUser;
    }

    public void logOut() {
        this.loggedUser = null;
    }

    public boolean isLoggedUserNotNull() {
        return loggedUser != null;
    }
}
