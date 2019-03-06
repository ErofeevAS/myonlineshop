package com.erofeev.st.alexei.myonlineshop.servlet;

import com.erofeev.st.alexei.myonlineshop.config.connection.ConfigurationManagerImpl;
import com.erofeev.st.alexei.myonlineshop.servlet.command.Command;
import com.erofeev.st.alexei.myonlineshop.servlet.command.CommandEnum;
import com.erofeev.st.alexei.myonlineshop.servlet.command.impl.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
        commands.put(CommandEnum.ITEMADD, new ItemAddCommand());
        commands.put(CommandEnum.ITEMSDELETE, new ItemDeleteCommand());
        commands.put(CommandEnum.ORDER, new OrderCommand());
        commands.put(CommandEnum.MYORDERS, new MyOrdersCommand());
        commands.put(CommandEnum.ORDERS, new OrdersCommand());
        commands.put(CommandEnum.CHANGEPASSWORD, new ChangePasswordCommand());
    }

    @Override
    public void destroy() {
        System.out.println("Dispatcher was destroy");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Command command = commands.get(CommandEnum.getCommand(request));
        String page = command.execute(request, response);

        if (page != null) {
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
            dispatcher.forward(request, response);
        } else {
//            page = ConfigurationManagerImpl.getInstance().getProperty(ConfigurationManagerImpl.LOGIN_PAGE);
//            response.sendRedirect(page);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
