package com.erofeev.st.alexei.myonlineshop.servlet.command.impl;

import com.erofeev.st.alexei.myonlineshop.config.connection.ConfigurationManagerImpl;
import com.erofeev.st.alexei.myonlineshop.repository.exception.RepositoryException;
import com.erofeev.st.alexei.myonlineshop.repository.exception.ServiceException;
import com.erofeev.st.alexei.myonlineshop.repository.model.Item;
import com.erofeev.st.alexei.myonlineshop.repository.model.User;
import com.erofeev.st.alexei.myonlineshop.service.ItemService;
import com.erofeev.st.alexei.myonlineshop.service.LoginRegistrationService;
import com.erofeev.st.alexei.myonlineshop.service.impl.ItemServiceImpl;
import com.erofeev.st.alexei.myonlineshop.service.impl.LoginRegistrationServiceImpl;
import com.erofeev.st.alexei.myonlineshop.service.model.ItemDTO;
import com.erofeev.st.alexei.myonlineshop.service.model.ProfileDTO;
import com.erofeev.st.alexei.myonlineshop.service.model.UserRegistrationDTO;
import com.erofeev.st.alexei.myonlineshop.servlet.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class RegistrationCommand implements Command {
    private LoginRegistrationService loginRegistrationService = LoginRegistrationServiceImpl.getInstance();
    private ItemService itemService = ItemServiceImpl.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = ConfigurationManagerImpl.getInstance().getProperty(ConfigurationManagerImpl.REGISTRATION_PAGE_PATH);
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String firstName = request.getParameter("firstname");
        String lastName = request.getParameter("lastname");
        if (email == null) {
            return page;
        }
        UserRegistrationDTO userDTO = new UserRegistrationDTO(email, password, firstName, lastName);
        String address = request.getParameter("address");
        String telephone = request.getParameter("telephone");

        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setAddress(address);
        profileDTO.setTelephone(telephone);

        try {
            User regUser = null;
            try {
                loginRegistrationService.registrationUser(userDTO, profileDTO);
                request.setAttribute("user", regUser);
                List<ItemDTO> items = itemService.findItems(1, 25);
                request.setAttribute("items", items);
                page = ConfigurationManagerImpl.getInstance().getProperty(ConfigurationManagerImpl.ITEMS_PAGE_PATH);
            } catch (ServiceException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
                page = ConfigurationManagerImpl.getInstance().getProperty(ConfigurationManagerImpl.REGISTRATION_PAGE_PATH);
            }

        } catch (RepositoryException e) {
            e.printStackTrace();
        }
        return page;
    }
}
