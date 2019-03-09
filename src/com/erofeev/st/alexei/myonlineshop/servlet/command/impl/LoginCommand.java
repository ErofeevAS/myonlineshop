package com.erofeev.st.alexei.myonlineshop.servlet.command.impl;

import com.erofeev.st.alexei.myonlineshop.config.connection.ConfigurationManagerImpl;
import com.erofeev.st.alexei.myonlineshop.repository.exception.ServiceException;
import com.erofeev.st.alexei.myonlineshop.repository.model.Permission;
import com.erofeev.st.alexei.myonlineshop.repository.model.User;
import com.erofeev.st.alexei.myonlineshop.service.DataBaseCreatorService;
import com.erofeev.st.alexei.myonlineshop.service.ItemService;
import com.erofeev.st.alexei.myonlineshop.service.LoginRegistrationService;
import com.erofeev.st.alexei.myonlineshop.service.UserService;
import com.erofeev.st.alexei.myonlineshop.service.converter.UserConverter;
import com.erofeev.st.alexei.myonlineshop.service.impl.DataBaseCreatorServiceImpl;
import com.erofeev.st.alexei.myonlineshop.service.impl.ItemServiceImpl;
import com.erofeev.st.alexei.myonlineshop.service.impl.LoginRegistrationServiceImpl;
import com.erofeev.st.alexei.myonlineshop.service.impl.UserServiceImpl;
import com.erofeev.st.alexei.myonlineshop.service.model.UserDTO;
import com.erofeev.st.alexei.myonlineshop.service.model.UserLoginDTO;
import com.erofeev.st.alexei.myonlineshop.service.model.UserSessionDTO;
import com.erofeev.st.alexei.myonlineshop.servlet.command.Command;
import com.erofeev.st.alexei.myonlineshop.servlet.validator.Validator;
import com.erofeev.st.alexei.myonlineshop.servlet.validator.impl.LoginValidatorImpl;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;

public class LoginCommand implements Command {
    private LoginRegistrationService loginRegistrationService = LoginRegistrationServiceImpl.getInstance();
    private UserService userService = UserServiceImpl.getInstance();
    private Validator validator = LoginValidatorImpl.getInstance();
    private static final String LOGIN_ERROR_MESSAGE = "wrong email or password";




    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String page = ConfigurationManagerImpl.getInstance().getProperty(ConfigurationManagerImpl.LOGIN_PAGE);
        if (!validator.isRequestValid(request)) {
            return page;
        }
        String email = request.getParameter("email").trim();
        String password = request.getParameter("password").trim();
        try {
            User user = userService.findByEmail(email, false);
            if (user == null) {
                request.setAttribute("error", LOGIN_ERROR_MESSAGE);
                return page;
            } else {
                if (userService.isValidPassword(user, password)) {
                    UserSessionDTO userSessionDTO = UserConverter.toSessionDTO(user);
                    HttpSession session = request.getSession();
                    session.setAttribute("user", userSessionDTO);
                    Permission permission = userSessionDTO.getPermission();
                    String baseURL = "/myonlineshop/shop?command=";
                    switch (permission.getName()) {
                        case CUSTOMER:
                            response.sendRedirect(baseURL + "items");
                            break;
                        case SELLER:
                            response.sendRedirect(baseURL + "orders");
                            break;
                        default:
                            response.sendRedirect(baseURL + "login");
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
