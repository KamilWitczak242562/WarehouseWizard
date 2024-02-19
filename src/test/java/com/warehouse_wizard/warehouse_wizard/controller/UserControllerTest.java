package com.warehouse_wizard.warehouse_wizard.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

import com.warehouse_wizard.warehouse_wizard.dto.UserDto;
import com.warehouse_wizard.warehouse_wizard.model.User;
import com.warehouse_wizard.warehouse_wizard.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.hamcrest.Matchers.is;

import java.util.HashMap;
import java.util.Map;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getUsersTest() throws Exception {
        Map<String, Integer> usersMap = new HashMap<>();
        usersMap.put("user1", 1);
        usersMap.put("user2", 2);

        when(userService.getAllUsers()).thenReturn(usersMap);

        mockMvc.perform(get("/api/users/allUsers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.user1", is(1)))
                .andExpect(jsonPath("$.user2", is(2)));
    }


    @Test
    public void registerUser() throws Exception {
        UserDto userDto = new UserDto("username", "password");
        User user = new User();

        when(userService.registerUser(any(User.class))).thenReturn(user);

        mockMvc.perform(post("/api/users/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void loginUser() throws Exception {
        UserDto userDto = new UserDto("username", "password");
        User user = new User();

        when(userService.logInUser(any(User.class))).thenReturn(user);

        mockMvc.perform(get("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isAccepted())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void logoutUser() throws Exception {
        when(userService.logOutUser()).thenReturn(true);

        mockMvc.perform(request(HttpMethod.GET, "/api/users/logout"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

}