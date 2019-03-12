package com.gmail.erofeev.st.alexei.myonlineshop.servlet.command.impl;

import com.gmail.erofeev.st.alexei.myonlineshop.config.connection.ConfigurationManagerImpl;
import com.gmail.erofeev.st.alexei.myonlineshop.servlet.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ItemAddPageCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        return ConfigurationManagerImpl.getInstance().getProperty(ConfigurationManagerImpl.ITEM_ADD_PAGE);
    }
}