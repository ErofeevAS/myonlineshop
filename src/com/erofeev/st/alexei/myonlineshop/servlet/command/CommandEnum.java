package com.erofeev.st.alexei.myonlineshop.servlet.command;

import com.erofeev.st.alexei.myonlineshop.repository.exception.ServiceException;
import com.erofeev.st.alexei.myonlineshop.service.DataBaseCreatorService;
import com.erofeev.st.alexei.myonlineshop.service.impl.DataBaseCreatorServiceImpl;
import com.erofeev.st.alexei.myonlineshop.servlet.command.exception.CommandNotFound;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;

public enum CommandEnum {
    LOGIN,
    REGISTRATION,
    REGISTRATION_PAGE,
    ITEMS,
    ORDERS,
    ITEM_ADD,
    ADD_ITEM_PAGE,
    ORDER,
    MY_ORDERS,
    ITEMS_DELETE,
    CHANGE_PASSWORD,
    LOGOUT,
    PROFILE_MENU,
    PROFILE_MENU_CHANGE;


    public static CommandEnum getCommand(String command) {

        try {
            return CommandEnum.valueOf(command.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Command does not exist");
        }
        return null;
    }


}
