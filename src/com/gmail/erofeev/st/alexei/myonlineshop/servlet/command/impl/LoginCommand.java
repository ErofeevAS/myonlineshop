package com.gmail.erofeev.st.alexei.myonlineshop.servlet.command.impl;

import com.gmail.erofeev.st.alexei.myonlineshop.config.connection.ConfigurationManagerImpl;
import com.gmail.erofeev.st.alexei.myonlineshop.repository.exception.ServiceException;
import com.gmail.erofeev.st.alexei.myonlineshop.repository.model.Permission;
import com.gmail.erofeev.st.alexei.myonlineshop.repository.model.User;
import com.gmail.erofeev.st.alexei.myonlineshop.service.UserService;
import com.gmail.erofeev.st.alexei.myonlineshop.service.converter.UserConverter;
import com.gmail.erofeev.st.alexei.myonlineshop.service.impl.UserServiceImpl;
import com.gmail.erofeev.st.alexei.myonlineshop.service.model.UserSessionDTO;
import com.gmail.erofeev.st.alexei.myonlineshop.servlet.DispatcherServlet;
import com.gmail.erofeev.st.alexei.myonlineshop.servlet.command.Command;
import com.gmail.erofeev.st.alexei.myonlineshop.servlet.command.CommandEnum;
import com.gmail.erofeev.st.alexei.myonlineshop.servlet.validator.Validator;
import com.gmail.erofeev.st.alexei.myonlineshop.servlet.validator.impl.LoginValidator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginCommand implements Command {
    private UserService userService = UserServiceImpl.getInstance();
    private Validator validator = LoginValidator.getInstance();
    private static final String LOGIN_ERROR_MESSAGE = "wrong email or password";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String page = ConfigurationManagerImpl.getInstance().getProperty(ConfigurationManagerImpl.LOGIN_PAGE);
        if (!validator.isRequestValid(request)) {
            return page;
        }
        String email = request.getParameter("email").trim();
        String password = request.getParameter("password").trim();
        try {
            User user = userService.findByEmail(email, false);
            if (user == null) {
                throw new ServletException(LOGIN_ERROR_MESSAGE);
            } else {
                if (userService.isValidPassword(user, password)) {
                    UserSessionDTO userSessionDTO = UserConverter.toSessionDTO(user);
                    HttpSession session = request.getSession();
                    session.setAttribute("user", userSessionDTO);
                    Permission permission = userSessionDTO.getPermission();
                    switch (permission.getName()) {
                        case CUSTOMER:
                            response.sendRedirect(DispatcherServlet.BASE_URL + CommandEnum.ITEMS.name().toLowerCase());
                            break;
                        case SELLER:
                            response.sendRedirect(DispatcherServlet.BASE_URL + CommandEnum.ORDERS.name().toLowerCase());
                            break;
                        default:
                            response.sendRedirect(DispatcherServlet.BASE_URL + CommandEnum.LOGIN.name().toLowerCase());
                            break;
                    }
                    return null;
                } else {
                    request.setAttribute("error", LOGIN_ERROR_MESSAGE);
                    return page;
                }
            }
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return page;
    }
}