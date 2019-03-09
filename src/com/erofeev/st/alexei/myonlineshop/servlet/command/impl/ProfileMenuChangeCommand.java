package com.erofeev.st.alexei.myonlineshop.servlet.command.impl;

import com.erofeev.st.alexei.myonlineshop.config.connection.ConfigurationManagerImpl;
import com.erofeev.st.alexei.myonlineshop.repository.exception.ServiceException;
import com.erofeev.st.alexei.myonlineshop.service.ProfileService;
import com.erofeev.st.alexei.myonlineshop.service.UserService;
import com.erofeev.st.alexei.myonlineshop.service.impl.ProfileServiceImpl;
import com.erofeev.st.alexei.myonlineshop.service.impl.UserServiceImpl;
import com.erofeev.st.alexei.myonlineshop.service.model.ProfileDTO;
import com.erofeev.st.alexei.myonlineshop.service.model.UserDTO;
import com.erofeev.st.alexei.myonlineshop.service.model.UserSessionDTO;
import com.erofeev.st.alexei.myonlineshop.servlet.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProfileMenuChangeCommand implements Command {
    private ProfileService profileService = ProfileServiceImpl.getInstance();
    private UserService userService = UserServiceImpl.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String page = ConfigurationManagerImpl.getInstance().getProperty(ConfigurationManagerImpl.PROFILE_MENU);
        String firstName = request.getParameter("firstname");
        String lastName = request.getParameter("lastname");
        String address = request.getParameter("address");
        String telephone = request.getParameter("telephone");
        ProfileDTO profileDTO = new ProfileDTO();
        UserSessionDTO user = (UserSessionDTO) request.getSession().getAttribute("user");
        Long id = user.getId();
        profileDTO.setId(id);
        profileDTO.setAddress(address);
        profileDTO.setTelephone(telephone);

        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName(firstName);
        userDTO.setLastName(lastName);
        userDTO.setId(id);

        user.setFirstName(firstName);
        user.setLastName(lastName);

        try {
            profileService.update(profileDTO);
            userService.updateInfo(userDTO);
            request.setAttribute("info", "profile was updated");
            request.setAttribute("profile", profileDTO);
            request.setAttribute("user", user);
        } catch (ServiceException e) {
            e.printStackTrace();
        }

        return page;
    }
}