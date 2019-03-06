package com.erofeev.st.alexei.myonlineshop.servlet.command.impl;

import com.erofeev.st.alexei.myonlineshop.config.connection.ConfigurationManagerImpl;
import com.erofeev.st.alexei.myonlineshop.repository.exception.ServiceException;
import com.erofeev.st.alexei.myonlineshop.service.UserService;
import com.erofeev.st.alexei.myonlineshop.service.impl.UserServiceImpl;
import com.erofeev.st.alexei.myonlineshop.service.model.ItemDTO;
import com.erofeev.st.alexei.myonlineshop.service.model.UserSessionDTO;
import com.erofeev.st.alexei.myonlineshop.servlet.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

public class ChangePasswordCommand implements Command {
    private UserService userService = UserServiceImpl.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String page = ConfigurationManagerImpl.getInstance().getProperty(ConfigurationManagerImpl.CHANGE_PASSWORD_PAGE);
        String oldPassword = request.getParameter("oldpassword");
        String newPassword = request.getParameter("newpassword");
        String reNewPassword = request.getParameter("repassword");
        UserSessionDTO user = (UserSessionDTO) request.getSession().getAttribute("user");
        Long id = user.getId();

        if ((oldPassword == null) || (newPassword == null) || (reNewPassword == null) || ("".equals(oldPassword) || ("".equals(newPassword) || ("".equals(reNewPassword))))) {
            page = ConfigurationManagerImpl.getInstance().getProperty(ConfigurationManagerImpl.CHANGE_PASSWORD_PAGE);
            return page;
        }
        oldPassword = oldPassword.trim();
        newPassword = newPassword.trim();
        reNewPassword = reNewPassword.trim();
        if (!newPassword.equals(reNewPassword)) {
            request.setAttribute("error", "password and repassword must match");
            page = ConfigurationManagerImpl.getInstance().getProperty(ConfigurationManagerImpl.CHANGE_PASSWORD_PAGE);
            return page;
        } else {
            try {
                userService.updatePassword(id, oldPassword, newPassword);
                request.setAttribute("info", " profile was updated");
                page = ConfigurationManagerImpl.getInstance().getProperty(ConfigurationManagerImpl.CHANGE_PASSWORD_PAGE);
            } catch (ServiceException e) {
                request.setAttribute("error", "wrong old password");
                page = ConfigurationManagerImpl.getInstance().getProperty(ConfigurationManagerImpl.CHANGE_PASSWORD_PAGE);
            }
        }

        return page;

    }

}
