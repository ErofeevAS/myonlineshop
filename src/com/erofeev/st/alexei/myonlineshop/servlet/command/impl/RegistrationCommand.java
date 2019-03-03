package com.erofeev.st.alexei.myonlineshop.servlet.command.impl;

import com.erofeev.st.alexei.myonlineshop.config.connection.ConfigurationManagerImpl;
import com.erofeev.st.alexei.myonlineshop.repository.exception.RepositoryException;
import com.erofeev.st.alexei.myonlineshop.repository.exception.ServiceException;
import com.erofeev.st.alexei.myonlineshop.repository.model.Item;
import com.erofeev.st.alexei.myonlineshop.repository.model.User;
import com.erofeev.st.alexei.myonlineshop.service.LoginRegistrationService;
import com.erofeev.st.alexei.myonlineshop.service.impl.LoginRegistrationServiceImpl;
import com.erofeev.st.alexei.myonlineshop.service.model.ProfileDTO;
import com.erofeev.st.alexei.myonlineshop.service.model.UserRegistrationDTO;
import com.erofeev.st.alexei.myonlineshop.servlet.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class RegistrationCommand implements Command {
    private LoginRegistrationService loginRegistrationService = LoginRegistrationServiceImpl.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = ConfigurationManagerImpl.getInstance().getProperty(ConfigurationManagerImpl.REGISTRATION_PAGE_PATH);
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String firstName = request.getParameter("firstname");
        String lastName = request.getParameter("lastname");
//        email = "spp@gmail.com";
//        password = "1234";
//        firstName = "Bob";
//        lastName = "Pupkin";
        UserRegistrationDTO userDTO = new UserRegistrationDTO(email, password, firstName, lastName);
        String address = request.getParameter("address");
        String telephone = request.getParameter("telephone");
//        address = "my address";
//        telephone = "+375291313666";
        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setAddress(address);
        profileDTO.setTelephone(telephone);
        if ((email == null) || (password == null) || (firstName == null) || (lastName == null) || (address == null) || (telephone == null)) {

        } else {
            try {
                User regUser = null;
                try {
                    loginRegistrationService.registrationUser(userDTO, profileDTO);
                } catch (ServiceException e) {
                    e.printStackTrace();
                }
                request.setAttribute("user", regUser);
                page = ConfigurationManagerImpl.getInstance().getProperty(ConfigurationManagerImpl.ITEMS_PAGE_PATH);
            } catch (RepositoryException e) {
                e.printStackTrace();
            }
        }
        return page;
    }
}
