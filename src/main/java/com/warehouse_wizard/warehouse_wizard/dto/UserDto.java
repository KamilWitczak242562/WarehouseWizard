package com.warehouse_wizard.warehouse_wizard.dto;

import com.warehouse_wizard.warehouse_wizard.entity.User;

import java.util.Optional;

public class UserDto {
    private String email;
    private String password;

    public UserDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public static UserDto toDto(User user) {
        return new UserDto(user.getEmail(), user.getPassword());
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
