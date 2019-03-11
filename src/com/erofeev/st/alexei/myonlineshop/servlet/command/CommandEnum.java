package com.erofeev.st.alexei.myonlineshop.servlet.command;

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
    CHANGE_PASSWORD_PAGE,
    LOGOUT,
    PROFILE_MENU,
    PROFILE_MENU_CHANGE,
    IMPORT_PAGE;


    public static CommandEnum getCommand(String command) {

        try {
            return CommandEnum.valueOf(command.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Command does not exist");
        }
        return null;
    }


}
