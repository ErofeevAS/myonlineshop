package com.erofeev.st.alexei.myonlineshop.servlet.command.impl;

import com.erofeev.st.alexei.myonlineshop.config.connection.ConfigurationManagerImpl;
import com.erofeev.st.alexei.myonlineshop.repository.exception.ServiceException;
import com.erofeev.st.alexei.myonlineshop.repository.model.Permission;
import com.erofeev.st.alexei.myonlineshop.repository.model.enums.Permissions;
import com.erofeev.st.alexei.myonlineshop.service.ItemService;
import com.erofeev.st.alexei.myonlineshop.service.LoginRegistrationService;
import com.erofeev.st.alexei.myonlineshop.service.converter.UserConverter;
import com.erofeev.st.alexei.myonlineshop.service.impl.ItemServiceImpl;
import com.erofeev.st.alexei.myonlineshop.service.impl.LoginRegistrationServiceImpl;
import com.erofeev.st.alexei.myonlineshop.service.model.UserDTO;
import com.erofeev.st.alexei.myonlineshop.service.model.UserLoginDTO;
import com.erofeev.st.alexei.myonlineshop.service.model.UserSessionDTO;
import com.erofeev.st.alexei.myonlineshop.servlet.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginCommand implements Command {
    private LoginRegistrationService loginRegistrationService = LoginRegistrationServiceImpl.getInstance();
    private ItemService itemService = ItemServiceImpl.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String page = null;
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if ((email == null) || (password == null) || ("".equals(email)) || ("".equals(password))) {
            request.setAttribute("error", "email or password is empty");
            page = ConfigurationManagerImpl.getInstance().getProperty(ConfigurationManagerImpl.LOGIN_PAGE);
        } else {
            UserDTO userDTO = null;
            try {
                userDTO = loginRegistrationService.loginUser(new UserLoginDTO(email.trim(), password.trim()));
            } catch (ServiceException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
            if (userDTO != null) {
                UserSessionDTO userSessionDTO = UserConverter.toSessionDTO(userDTO);
                HttpSession session = request.getSession();
                session.setAttribute("user", userSessionDTO);
                Permission permission = userSessionDTO.getPermission();
                String baseURL = "/myonlineshop/shop?command=";
                System.out.println(permission.getName());
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
                request.setAttribute("error", "WRONG PASS OR EMAIL");
                page = ConfigurationManagerImpl.getInstance().getProperty(ConfigurationManagerImpl.LOGIN_PAGE);
            }

        }
        return page;
    }


}
