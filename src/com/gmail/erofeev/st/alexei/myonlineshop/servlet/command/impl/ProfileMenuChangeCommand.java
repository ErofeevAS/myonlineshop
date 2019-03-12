package com.gmail.erofeev.st.alexei.myonlineshop.servlet.command.impl;

import com.gmail.erofeev.st.alexei.myonlineshop.config.connection.ConfigurationManagerImpl;
import com.gmail.erofeev.st.alexei.myonlineshop.repository.exception.ServiceException;
import com.gmail.erofeev.st.alexei.myonlineshop.service.ProfileService;
import com.gmail.erofeev.st.alexei.myonlineshop.service.UserService;
import com.gmail.erofeev.st.alexei.myonlineshop.service.impl.ProfileServiceImpl;
import com.gmail.erofeev.st.alexei.myonlineshop.service.impl.UserServiceImpl;
import com.gmail.erofeev.st.alexei.myonlineshop.service.model.ProfileDTO;
import com.gmail.erofeev.st.alexei.myonlineshop.service.model.UserDTO;
import com.gmail.erofeev.st.alexei.myonlineshop.service.model.UserSessionDTO;
import com.gmail.erofeev.st.alexei.myonlineshop.servlet.command.Command;
import com.gmail.erofeev.st.alexei.myonlineshop.servlet.validator.Validator;
import com.gmail.erofeev.st.alexei.myonlineshop.servlet.validator.impl.ProfileValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ProfileMenuChangeCommand implements Command {
    private ProfileService profileService = ProfileServiceImpl.getInstance();
    private UserService userService = UserServiceImpl.getInstance();
    private Validator validator = ProfileValidator.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = ConfigurationManagerImpl.getInstance().getProperty(ConfigurationManagerImpl.PROFILE_MENU);
        if (!validator.isRequestValid(request)) {
            return page;
        }
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
