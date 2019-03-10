package com.erofeev.st.alexei.myonlineshop.servlet;

import com.erofeev.st.alexei.myonlineshop.repository.exception.RepositoryException;
import com.erofeev.st.alexei.myonlineshop.repository.exception.ServiceException;
import com.erofeev.st.alexei.myonlineshop.service.DataBaseCreatorService;
import com.erofeev.st.alexei.myonlineshop.service.UserService;
import com.erofeev.st.alexei.myonlineshop.service.impl.DataBaseCreatorServiceImpl;
import com.erofeev.st.alexei.myonlineshop.service.impl.UserServiceImpl;
import com.erofeev.st.alexei.myonlineshop.servlet.command.Command;
import com.erofeev.st.alexei.myonlineshop.servlet.command.CommandEnum;
import com.erofeev.st.alexei.myonlineshop.servlet.command.impl.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DispatcherServlet extends HttpServlet {
    private Map<CommandEnum, Command> commands = new HashMap<>();
    private UserService userService = UserServiceImpl.getInstance();
    public final static String BASE_URL = "/myonlineshop/shop?command=";

    @Override
    public void init() {
        System.out.println("Dispatcher init");
        commands.put(CommandEnum.ITEMS, new ItemsCommand());
        commands.put(CommandEnum.LOGIN, new LoginCommand());
        commands.put(CommandEnum.LOGOUT, new LogoutCommand());
        commands.put(CommandEnum.REGISTRATION, new RegistrationCommand());
        commands.put(CommandEnum.REGISTRATION_PAGE, new RegistrationPageCommand());
        commands.put(CommandEnum.ITEM_ADD, new ItemAddCommand());
        commands.put(CommandEnum.ADD_ITEM_PAGE, new ItemAddPageCommand());
        commands.put(CommandEnum.ITEMS_DELETE, new ItemDeleteCommand());
        commands.put(CommandEnum.ORDER, new OrderCommand());
        commands.put(CommandEnum.MY_ORDERS, new MyOrdersCommand());
        commands.put(CommandEnum.ORDERS, new OrdersCommand());
        commands.put(CommandEnum.CHANGE_PASSWORD, new ChangePasswordCommand());
        commands.put(CommandEnum.PROFILE_MENU, new ProfileMenuCommand());
        commands.put(CommandEnum.PROFILE_MENU_CHANGE, new ProfileMenuChangeCommand());
        initDataBase();
    }

    @Override
    public void destroy() {
        System.out.println("Dispatcher was destroy");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("command");

        Command command = commands.get(CommandEnum.getCommand(action));
        if (command != null) {
            String page = null;
            try {
                page = command.execute(request, response);
            } catch (RepositoryException | ServiceException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
            if (page != null) {
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
                dispatcher.forward(request, response);
            } else {
                System.out.println("command does not exist");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    private void initDataBase() {
        try {
            if (userService.getAmountOfUser() == 0) {
                ServletContext context = getServletContext();
                String fullPath1 = context.getRealPath("/WEB-INF/sql/myshop.sql");
                String fullPath2 = context.getRealPath("/WEB-INF/sql/populate_table.sql");
                File fileCreator = new File(fullPath1);
                File filePopulator = new File(fullPath2);
                DataBaseCreatorService dataBaseCreatorService = DataBaseCreatorServiceImpl.getInstance();
                try {
                    dataBaseCreatorService.createDataBaseFromFile(fileCreator);
                    dataBaseCreatorService.createDataBaseFromFile(filePopulator);
                } catch (ServiceException e) {
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                }
            }
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }
}
