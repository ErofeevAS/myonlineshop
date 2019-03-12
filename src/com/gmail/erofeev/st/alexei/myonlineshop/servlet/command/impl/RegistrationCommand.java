package com.gmail.erofeev.st.alexei.myonlineshop.servlet.command.impl;

import com.gmail.erofeev.st.alexei.myonlineshop.config.connection.ConfigurationManagerImpl;
import com.gmail.erofeev.st.alexei.myonlineshop.repository.exception.ServiceException;
import com.gmail.erofeev.st.alexei.myonlineshop.repository.model.Role;
import com.gmail.erofeev.st.alexei.myonlineshop.repository.model.User;
import com.gmail.erofeev.st.alexei.myonlineshop.service.ProfileService;
import com.gmail.erofeev.st.alexei.myonlineshop.service.RoleService;
import com.gmail.erofeev.st.alexei.myonlineshop.service.SecureService;
import com.gmail.erofeev.st.alexei.myonlineshop.service.UserService;
import com.gmail.erofeev.st.alexei.myonlineshop.service.impl.ProfileServiceImpl;
import com.gmail.erofeev.st.alexei.myonlineshop.service.impl.RoleServiceImpl;
import com.gmail.erofeev.st.alexei.myonlineshop.service.impl.SecureServiceImpl;
import com.gmail.erofeev.st.alexei.myonlineshop.service.impl.UserServiceImpl;
import com.gmail.erofeev.st.alexei.myonlineshop.service.model.ProfileDTO;
import com.gmail.erofeev.st.alexei.myonlineshop.service.model.UserRegistrationDTO;
import com.gmail.erofeev.st.alexei.myonlineshop.servlet.command.Command;
import com.gmail.erofeev.st.alexei.myonlineshop.servlet.validator.Validator;
import com.gmail.erofeev.st.alexei.myonlineshop.servlet.validator.impl.RegistrationValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegistrationCommand implements Command {
    private RoleService roleService = RoleServiceImpl.getInstance();
    private ProfileService profileService = ProfileServiceImpl.getInstance();
    private UserService userService = UserServiceImpl.getInstance();
    private Validator validator = RegistrationValidator.getInstance();
    private SecureService secureService = SecureServiceImpl.getInstance();
    private final String DEFAULT_ROLE_NAME = "CUSTOMER";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = ConfigurationManagerImpl.getInstance().getProperty(ConfigurationManagerImpl.REGISTRATION_PAGE);
        if (!validator.isRequestValid(request)) {
            return page;
        }
        UserRegistrationDTO userDTO = createUserRegistrationDTO(request);
        ProfileDTO profileDTO = createProfileDTO(request);
        try {
            String email = userDTO.getEmail();
            User user = userService.findByEmail(email, false);
            if (user == null) {
                Role role = roleService.findRoleNyName(DEFAULT_ROLE_NAME);
                userDTO.setRole(role);
                String password = userDTO.getPassword();
                userDTO.setPassword(secureService.hashPassword(password));
                user = userService.save(userDTO);
                profileDTO.setId(user.getId());
                profileService.save(profileDTO);
                request.setAttribute("info", "user was registered");
                page = ConfigurationManagerImpl.getInstance().getProperty(ConfigurationManagerImpl.REGISTRATION_PAGE);
            } else {
                request.setAttribute("error", "email is reserved");
                return page;
            }

        } catch (ServiceException e) {
            request.setAttribute("error", e.getMessage());
            e.printStackTrace();
            page = ConfigurationManagerImpl.getInstance().getProperty(ConfigurationManagerImpl.REGISTRATION_PAGE);
        }
        return page;
    }

    private UserRegistrationDTO createUserRegistrationDTO(HttpServletRequest request) {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String firstName = request.getParameter("firstname");
        String lastName = request.getParameter("lastname");
        UserRegistrationDTO userDTO = new UserRegistrationDTO(email, password, firstName, lastName);
        return userDTO;
    }

    private ProfileDTO createProfileDTO(HttpServletRequest request) {
        String address = request.getParameter("address");
        String telephone = request.getParameter("telephone");
        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setAddress(address);
        profileDTO.setTelephone(telephone);
        return profileDTO;
    }
}
