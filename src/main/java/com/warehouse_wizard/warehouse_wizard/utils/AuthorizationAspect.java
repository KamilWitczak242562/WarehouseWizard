package com.warehouse_wizard.warehouse_wizard.utils;

import com.warehouse_wizard.warehouse_wizard.exception.UnauthorizedException;
import lombok.AllArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
@AllArgsConstructor
public class AuthorizationAspect {
    private final LoggedUser loggedUser;
    @Before("@annotation(RequiresLoggedInUser)")
    public void checkLoggedInUser() {
        if (!loggedUser.isLoggedUserNotNull()) {
            throw new UnauthorizedException("User is not logged in");
        }
    }
}
