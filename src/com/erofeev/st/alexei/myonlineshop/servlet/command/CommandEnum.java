package com.erofeev.st.alexei.myonlineshop.servlet.command;

import com.erofeev.st.alexei.myonlineshop.servlet.command.exception.CommandNotFound;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public enum CommandEnum {
    LOGIN,
    REGISTRATION,
    ITEMS,
    ORDERS,
    ADDITEM,
    ORDER,
    MYORDERS;


    public static CommandEnum getCommand(HttpServletRequest request) {
        String action = request.getParameter("command");
        if ((action == null) || (action.isEmpty())) {
            return LOGIN;
        } else {
            return CommandEnum.valueOf(action.toUpperCase());
        }
    }
}
