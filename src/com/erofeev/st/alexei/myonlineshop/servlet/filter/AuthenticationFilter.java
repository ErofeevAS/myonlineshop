package com.erofeev.st.alexei.myonlineshop.servlet.filter;


import com.erofeev.st.alexei.myonlineshop.repository.model.Permission;
import com.erofeev.st.alexei.myonlineshop.service.model.UserSessionDTO;
import com.erofeev.st.alexei.myonlineshop.servlet.command.CommandEnum;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class AuthenticationFilter implements Filter {
    private static final Set<CommandEnum> CUSTOMER_AVAILABLE = new HashSet<>();
    private static final Set<CommandEnum> SELLER_AVAILABLE = new HashSet<>();
    public static final String LOGIN_PATH = "/index.jsp";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        CUSTOMER_AVAILABLE.add(CommandEnum.ITEMS);
        CUSTOMER_AVAILABLE.add(CommandEnum.CHANGE_PASSWORD);
        CUSTOMER_AVAILABLE.add(CommandEnum.ORDER);
        CUSTOMER_AVAILABLE.add(CommandEnum.MY_ORDERS);
        CUSTOMER_AVAILABLE.add(CommandEnum.LOGOUT);
        CUSTOMER_AVAILABLE.add(CommandEnum.CHANGE_PASSWORD);
        CUSTOMER_AVAILABLE.add(CommandEnum.REGISTRATION_PAGE);
        CUSTOMER_AVAILABLE.add(CommandEnum.REGISTRATION);
        CUSTOMER_AVAILABLE.add(CommandEnum.PROFILE_MENU);
        CUSTOMER_AVAILABLE.add(CommandEnum.PROFILE_MENU_CHANGE);

//        SELLER_AVAILABLE.add(CommandEnum.ITEMS);
        SELLER_AVAILABLE.add(CommandEnum.ITEMS_DELETE);
        SELLER_AVAILABLE.add(CommandEnum.ITEM_ADD);
        SELLER_AVAILABLE.add(CommandEnum.ADD_ITEM_PAGE);
        SELLER_AVAILABLE.add(CommandEnum.ORDERS);
        SELLER_AVAILABLE.add(CommandEnum.CHANGE_PASSWORD);
        SELLER_AVAILABLE.add(CommandEnum.LOGOUT);
        SELLER_AVAILABLE.add(CommandEnum.REGISTRATION);
        SELLER_AVAILABLE.add(CommandEnum.REGISTRATION_PAGE);
        SELLER_AVAILABLE.add(CommandEnum.PROFILE_MENU);
        SELLER_AVAILABLE.add(CommandEnum.PROFILE_MENU_CHANGE);


    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession(false);
        String command = request.getParameter("command");
        if (session == null) {
            defaultRequest(servletRequest, servletResponse, filterChain, request, response, command);
        } else {
            UserSessionDTO user = (UserSessionDTO) session.getAttribute("user");
            if (user == null) {
                defaultRequest(servletRequest, servletResponse, filterChain, request, response, command);
            } else {
                CommandEnum commandEnum = CommandEnum.getCommand(command);
                Permission permission = user.getPermission();
                switch (permission.getName()) {
                    case CUSTOMER:
                        if (CUSTOMER_AVAILABLE.contains(commandEnum)) {
                            filterChain.doFilter(request, response);
                        } else {
                            session.removeAttribute("user");
                            response.sendRedirect(request.getContextPath() + LOGIN_PATH);
                        }
                        break;
                    case SELLER:
                        if (SELLER_AVAILABLE.contains(commandEnum)) {
                            filterChain.doFilter(request, response);
                        } else {
                            session.removeAttribute("user");
                            response.sendRedirect(request.getContextPath() + LOGIN_PATH);
                        }
                        break;
                    default:
                        session.removeAttribute("user");
                        response.sendRedirect(request.getContextPath() + LOGIN_PATH);
                        break;
                }
            }
        }
    }

    @Override
    public void destroy() {

    }

    private void defaultRequest(ServletRequest request, ServletResponse response, FilterChain chain, HttpServletRequest req, HttpServletResponse res, String command) throws IOException, ServletException {
        if (req.getMethod().equalsIgnoreCase("post")) {
            CommandEnum commandEnum = CommandEnum.getCommand(command);
            if (commandEnum == CommandEnum.LOGIN || commandEnum == CommandEnum.REGISTRATION_PAGE || commandEnum == CommandEnum.REGISTRATION) {
                chain.doFilter(request, response);
            } else {
                res.sendRedirect(req.getContextPath() + LOGIN_PATH);
            }

        } else {
            res.sendRedirect(req.getContextPath() + LOGIN_PATH);
        }
    }
}
