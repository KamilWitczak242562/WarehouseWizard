package com.warehouse_wizard.warehouse_wizard.mapper;

import com.warehouse_wizard.warehouse_wizard.dto.UserDto;
import com.warehouse_wizard.warehouse_wizard.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

    public static UserDto userToUserDTO(User user) {
        UserDto userDTO = new UserDto();
        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setEmail(user.getEmail());
        return userDTO;
    }

    public static User userDTOToUser(UserDto userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setEmail(userDTO.getEmail());
        user.setEmail(userDTO.getEmail());
        return user;
    }
}
