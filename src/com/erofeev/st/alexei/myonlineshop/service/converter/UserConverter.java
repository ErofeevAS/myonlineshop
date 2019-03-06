package com.erofeev.st.alexei.myonlineshop.service.converter;

import com.erofeev.st.alexei.myonlineshop.repository.model.Role;
import com.erofeev.st.alexei.myonlineshop.repository.model.User;
import com.erofeev.st.alexei.myonlineshop.service.model.UserDTO;
import com.erofeev.st.alexei.myonlineshop.service.model.UserLoginDTO;
import com.erofeev.st.alexei.myonlineshop.service.model.UserRegistrationDTO;
import com.erofeev.st.alexei.myonlineshop.service.model.UserSessionDTO;

public class UserConverter {


    public static User fromUserRegistrationDTO(UserRegistrationDTO userDTO) {
        User user = new User();
        String name = userDTO.getFirstName();
        String surName = userDTO.getLastName();
        String email = userDTO.getEmail();
        String password = userDTO.getPassword();
        user.setEmail(email);
        user.setFirstName(name);
        user.setLastName(surName);
        user.setPassword(password);
        return user;
    }

    public static User fromDTO(UserDTO userDTO) {
        User user = new User();
        Long id = userDTO.getId();
        String name = userDTO.getFirstName();
        String surName = userDTO.getLastName();
        Role role = userDTO.getRole();
        user.setId(id);
        user.setFirstName(name);
        user.setLastName(surName);
        user.setRole(role);
        return user;
    }

    public static UserDTO toDTO(User user) {
        UserDTO userDTO = new UserDTO();
        Long id = user.getId();
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        Role role = user.getRole();
        userDTO.setFirstName(firstName);
        userDTO.setLastName(lastName);
        userDTO.setRole(role);
        userDTO.setId(id);
        return userDTO;
    }

    public static UserSessionDTO toSessionDTO(UserDTO userDTO){
        UserSessionDTO userSessionDTO = new UserSessionDTO();
        userSessionDTO.setEmail(userDTO.getEmail());
        userSessionDTO.setFirstName(userDTO.getFirstName());
        userSessionDTO.setLastName(userDTO.getLastName());
        userSessionDTO.setId(userDTO.getId());
        userSessionDTO.setPermission(userDTO.getRole().getPermissions().get(0));
        return userSessionDTO;
    }

}
