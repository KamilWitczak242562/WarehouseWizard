package com.warehouse_wizard.warehouse_wizard.utils;

import com.warehouse_wizard.warehouse_wizard.entity.User;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class LoggedUser {
    private static LoggedUser loggedUser;

    private User user;
    private ScheduledExecutorService scheduler;

    public LoggedUser(User user) {
        this.user = user;
        this.scheduler = Executors.newScheduledThreadPool(1);
        scheduler.schedule(this::logOut, 30, TimeUnit.MINUTES);
    }

    public static LoggedUser getLoggedUser(User user) {
        if (loggedUser == null) {
            loggedUser = new LoggedUser(user);
        }
        return loggedUser;
    }

    public void logOut() {
        this.user = null;
        scheduler.shutdown();
    }
}
