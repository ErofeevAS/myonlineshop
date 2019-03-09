package com.erofeev.st.alexei.myonlineshop.servlet.command.impl;

import com.erofeev.st.alexei.myonlineshop.config.connection.ConfigurationManagerImpl;
import com.erofeev.st.alexei.myonlineshop.repository.exception.ServiceException;
import com.erofeev.st.alexei.myonlineshop.repository.model.User;
import com.erofeev.st.alexei.myonlineshop.service.ProfileService;
import com.erofeev.st.alexei.myonlineshop.service.UserService;
import com.erofeev.st.alexei.myonlineshop.service.impl.ProfileServiceImpl;
import com.erofeev.st.alexei.myonlineshop.service.impl.UserServiceImpl;
import com.erofeev.st.alexei.myonlineshop.service.model.ProfileDTO;
import com.erofeev.st.alexei.myonlineshop.service.model.UserSessionDTO;
import com.erofeev.st.alexei.myonlineshop.servlet.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProfileMenuCommand implements Command {
    private ProfileService profileService = ProfileServiceImpl.getInstance();
    private UserService userService = UserServiceImpl.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        UserSessionDTO user = (UserSessionDTO) request.getSession().getAttribute("user");
        try {
            ProfileDTO profileDTO = profileService.findById(user.getId());
            request.setAttribute("profile", profileDTO);
            request.setAttribute("user", user);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        return ConfigurationManagerImpl.getInstance().getProperty(ConfigurationManagerImpl.PROFILE_MENU);
    }
}
