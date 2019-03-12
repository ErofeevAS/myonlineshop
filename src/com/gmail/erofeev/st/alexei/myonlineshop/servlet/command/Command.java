package com.gmail.erofeev.st.alexei.myonlineshop.servlet.command;

import com.gmail.erofeev.st.alexei.myonlineshop.repository.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface Command {

    String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServiceException, ServletException;
}
