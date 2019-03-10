package com.erofeev.st.alexei.myonlineshop.servlet.command.impl;

import com.erofeev.st.alexei.myonlineshop.config.connection.ConfigurationManagerImpl;
import com.erofeev.st.alexei.myonlineshop.repository.exception.RepositoryException;
import com.erofeev.st.alexei.myonlineshop.repository.exception.ServiceException;
import com.erofeev.st.alexei.myonlineshop.repository.model.Role;
import com.erofeev.st.alexei.myonlineshop.repository.model.User;
import com.erofeev.st.alexei.myonlineshop.service.LoginRegistrationService;
import com.erofeev.st.alexei.myonlineshop.service.impl.LoginRegistrationServiceImpl;
import com.erofeev.st.alexei.myonlineshop.service.model.ProfileDTO;
import com.erofeev.st.alexei.myonlineshop.service.model.UserRegistrationDTO;
import com.erofeev.st.alexei.myonlineshop.servlet.command.Command;
import com.erofeev.st.alexei.myonlineshop.servlet.validator.Validator;
import com.erofeev.st.alexei.myonlineshop.servlet.validator.impl.RegistrationValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegistrationCommand implements Command {
    private LoginRegistrationService loginRegistrationService = LoginRegistrationServiceImpl.getInstance();
    private Validator validator = RegistrationValidator.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = ConfigurationManagerImpl.getInstance().getProperty(ConfigurationManagerImpl.REGISTRATION_PAGE);
        if (!validator.isRequestValid(request)) {
            return page;
        }
        UserRegistrationDTO userDTO = createUserRegistrationDTO(request);
        ProfileDTO profileDTO = createProfileDTO(request);

        try {
            try {
                Role role = new Role("CUSTOMER");
                role.setId(2L);
                loginRegistrationService.registrationUser(userDTO, profileDTO, role);
                request.setAttribute("info", "user was registered");

                page = ConfigurationManagerImpl.getInstance().getProperty(ConfigurationManagerImpl.REGISTRATION_PAGE);
            } catch (ServiceException e) {
                request.setAttribute("error", e.getMessage());
                e.printStackTrace();
                page = ConfigurationManagerImpl.getInstance().getProperty(ConfigurationManagerImpl.REGISTRATION_PAGE);
            }

        } catch (RepositoryException e) {
            e.printStackTrace();
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
