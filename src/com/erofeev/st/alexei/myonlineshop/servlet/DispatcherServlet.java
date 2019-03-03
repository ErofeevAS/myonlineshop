package com.erofeev.st.alexei.myonlineshop.servlet;

import com.erofeev.st.alexei.myonlineshop.config.ConfigurationManager;
import com.erofeev.st.alexei.myonlineshop.config.connection.ConfigurationManagerImpl;
import com.erofeev.st.alexei.myonlineshop.repository.model.User;
import com.erofeev.st.alexei.myonlineshop.service.LoginRegistrationService;
import com.erofeev.st.alexei.myonlineshop.service.UserService;
import com.erofeev.st.alexei.myonlineshop.service.impl.LoginRegistrationServiceImpl;
import com.erofeev.st.alexei.myonlineshop.service.impl.UserServiceImpl;
import com.erofeev.st.alexei.myonlineshop.service.model.UserDTO;
import com.erofeev.st.alexei.myonlineshop.service.model.UserLoginDTO;
import com.erofeev.st.alexei.myonlineshop.servlet.command.Command;
import com.erofeev.st.alexei.myonlineshop.servlet.command.CommandEnum;
import com.erofeev.st.alexei.myonlineshop.servlet.command.exception.CommandNotFound;
import com.erofeev.st.alexei.myonlineshop.servlet.command.impl.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class DispatcherServlet extends HttpServlet {
    private Map<CommandEnum, Command> commands = new HashMap<>();

    @Override
    public void init() throws ServletException {
        System.out.println("Dispatcher init");
        commands.put(CommandEnum.ITEMS, new ItemsCommand());
        commands.put(CommandEnum.LOGIN, new LoginCommand());
        commands.put(CommandEnum.REGISTRATION, new RegistrationCommand());
        commands.put(CommandEnum.ADDITEM, new ItemCommand());

    }

    @Override
    public void destroy() {
        System.out.println("Dispatcher was destroy");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Command command = commands.get(CommandEnum.getCommand(request));
        System.out.println(request.getRequestURI());
        System.out.println("Command from request: " + command.getClass().getSimpleName());
        String page = command.execute(request, response);
        System.out.println("Command from request: "+ page);
        if (page != null) {
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
            dispatcher.forward(request, response);
        } else {
            page = ConfigurationManagerImpl.getInstance().getProperty(ConfigurationManagerImpl.INDEX_PAGE_PATH);
            request.getSession().setAttribute("nullPage", "NULL PAGE ERROR");
            response.sendRedirect(request.getContextPath() + page);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
