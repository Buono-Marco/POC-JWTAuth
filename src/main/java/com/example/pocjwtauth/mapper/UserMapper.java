package com.example.pocjwtauth.mapper;

import com.example.pocjwtauth.dto.UserDTO;
import com.example.pocjwtauth.entity.Role;
import com.example.pocjwtauth.entity.User;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
public class UserMapper {

    public static User mapToEntity(UserDTO userDTO) {

        log.debug("userDTO to convert = {} ", userDTO);

        if (userDTO == null) {
            return null;
        }

        User user = new User();
        user.setId(userDTO.getId());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());

        Role role = new Role();
        role.setRoleName(userDTO.getRoleName());
        user.setRole(role);

        log.debug("successfully converted userDTO to entity = {} ", user);

        return user;
    }

    public static UserDTO mapToDTO(User user) {

        log.debug("user entity to convert = {} ", user);

        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());
        userDTO.setRoleName(user.getRole().getRoleName());

        log.debug("successfully converted user entity to DTO = {} ", userDTO);

        return userDTO;
    }
}
