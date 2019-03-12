package com.gmail.erofeev.st.alexei.myonlineshop.servlet.command.impl;

import com.gmail.erofeev.st.alexei.myonlineshop.config.connection.ConfigurationManagerImpl;
import com.gmail.erofeev.st.alexei.myonlineshop.repository.exception.ServiceException;
import com.gmail.erofeev.st.alexei.myonlineshop.service.UserService;
import com.gmail.erofeev.st.alexei.myonlineshop.service.impl.UserServiceImpl;
import com.gmail.erofeev.st.alexei.myonlineshop.service.model.UserSessionDTO;
import com.gmail.erofeev.st.alexei.myonlineshop.servlet.command.Command;
import com.gmail.erofeev.st.alexei.myonlineshop.servlet.validator.Validator;
import com.gmail.erofeev.st.alexei.myonlineshop.servlet.validator.impl.PasswordValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ChangePasswordCommand implements Command {
    private UserService userService = UserServiceImpl.getInstance();
    private Validator validator = PasswordValidator.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = ConfigurationManagerImpl.getInstance().getProperty(ConfigurationManagerImpl.CHANGE_PASSWORD_PAGE);
        if (!validator.isRequestValid(request)) {
            return page;
        }
        String oldPassword = request.getParameter("oldpassword").trim();
        String newPassword = request.getParameter("password").trim();
        UserSessionDTO userSessionDTO = (UserSessionDTO) request.getSession().getAttribute("user");
        Long id = userSessionDTO.getId();
        try {
            userService.updatePassword(id, oldPassword, newPassword);
            request.setAttribute("info", " profile was updated");
        } catch (ServiceException e) {
            request.setAttribute("error", "wrong old password");
        }
        return page;
    }
}
