package com.erofeev.st.alexei.myonlineshop.service.converter;

import com.erofeev.st.alexei.myonlineshop.repository.model.User;
import com.erofeev.st.alexei.myonlineshop.service.model.UserLoginDTO;
import com.erofeev.st.alexei.myonlineshop.service.model.UserRegistrationDTO;

public class UserConverterImpl {
    public UserRegistrationDTO toDTO(User user) {
        return null;
    }

    public static User fromDTO(UserRegistrationDTO userDTO) {
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

}
