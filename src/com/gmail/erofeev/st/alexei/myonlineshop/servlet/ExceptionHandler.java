package com.gmail.erofeev.st.alexei.myonlineshop.servlet;

import com.gmail.erofeev.st.alexei.myonlineshop.config.connection.ConfigurationManagerImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ExceptionHandler extends HttpServlet {

    @Override
    public void destroy() {
        System.out.println("destroy exception handler servlet");
    }

    @Override
    public void init() {
        System.out.println("init exception handler servlet");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Throwable throwable = (Throwable) req.getAttribute(RequestDispatcher.ERROR_EXCEPTION);
        Integer statusCode = (Integer) req.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        String servletName = (String) req.getAttribute(RequestDispatcher.ERROR_SERVLET_NAME);

        if (servletName == null) {
            servletName = "Unknown";
        }
        String requestUri = (String) req.getAttribute("javax.servlet.error.request_uri");

        if (requestUri == null) {
            requestUri = "Unknown";
        }
        String message = throwable.getMessage();
        if ("wrong email or password".equals(message)) {
            String page = ConfigurationManagerImpl.getInstance().getProperty(ConfigurationManagerImpl.LOGIN_PAGE);
            req.setAttribute("error", message + " from exception request");
            getServletContext().getRequestDispatcher(page).forward(req, resp);
        }
        System.out.println("Error info");
        System.out.println("status code: " + statusCode);
        System.out.println("Servlet name: " + servletName);
        System.out.println("Exception type: " + throwable.getClass().getSimpleName());
        System.out.println("The request uri: " + requestUri);
        String page = ConfigurationManagerImpl.getInstance().getProperty(ConfigurationManagerImpl.ERROR_PAGE);
        getServletContext().getRequestDispatcher(page).forward(req, resp);
    }
}
