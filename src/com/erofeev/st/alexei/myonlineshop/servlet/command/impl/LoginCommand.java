package com.erofeev.st.alexei.myonlineshop.servlet.command.impl;

import com.erofeev.st.alexei.myonlineshop.config.connection.ConfigurationManagerImpl;
import com.erofeev.st.alexei.myonlineshop.repository.model.Item;
import com.erofeev.st.alexei.myonlineshop.service.ItemService;
import com.erofeev.st.alexei.myonlineshop.service.LoginRegistrationService;
import com.erofeev.st.alexei.myonlineshop.service.impl.ItemServiceImpl;
import com.erofeev.st.alexei.myonlineshop.service.impl.LoginRegistrationServiceImpl;
import com.erofeev.st.alexei.myonlineshop.service.model.ItemDTO;
import com.erofeev.st.alexei.myonlineshop.service.model.UserDTO;
import com.erofeev.st.alexei.myonlineshop.service.model.UserLoginDTO;
import com.erofeev.st.alexei.myonlineshop.servlet.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class LoginCommand implements Command {
    private LoginRegistrationService loginRegistrationService = LoginRegistrationServiceImpl.getInstance();
    private ItemService itemService = ItemServiceImpl.getInstance();
    private String pas = "1234";
    private String emailUser = "spokeman152@gmail.com";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = ConfigurationManagerImpl.getInstance().getProperty(ConfigurationManagerImpl.LOGIN_PAGE_PATH);
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        //UserDTO userDTO = loginRegistrationService.loginUser(new UserLoginDTO(email, password));
        if ((email != null) && (password != null)) {
            if (check(password, pas, email, emailUser)) {
                List<ItemDTO> items = itemService.findItems(1, 10);
                request.setAttribute("items", items);
                page = ConfigurationManagerImpl.getInstance().getProperty(ConfigurationManagerImpl.ITEMS_PAGE_PATH);
            } else {
                request.setAttribute("errorLoginPassMessage", "WRONG PASS OR EMAIL");
                page = ConfigurationManagerImpl.getInstance().getProperty(ConfigurationManagerImpl.LOGIN_PAGE_PATH);
            }
        }
        return page;
    }

    private boolean check(String password1, String password2, String email1, String email2) {
        return (password1.equals(password2) && (email1.equals(email2)));
    }

}
