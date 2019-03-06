package com.erofeev.st.alexei.myonlineshop.servlet.filter;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class LogFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("log filter was created");

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        System.out.println(request.getRequestURI());
        List<String> params = new ArrayList<String>(request.getParameterMap().keySet());

        for (String param : params) {
            System.out.println(param + " = " + request.getParameter(param));
        }


        System.out.println("---------");
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        System.out.println("filter was destroyed");

    }
}
