package com.erofeev.st.alexei.myonlineshop.servlet.command;

import com.erofeev.st.alexei.myonlineshop.repository.exception.RepositoryException;
import com.erofeev.st.alexei.myonlineshop.repository.exception.ServiceException;
import com.sun.deploy.net.HttpRequest;
import com.sun.deploy.net.HttpResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface Command {

    String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, RepositoryException, ServiceException, ServletException;

}
