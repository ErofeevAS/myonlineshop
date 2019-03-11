package com.erofeev.st.alexei.myonlineshop.servlet.validator.impl;

import com.erofeev.st.alexei.myonlineshop.servlet.validator.Validator;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

public class PasswordValidator extends Validator {
    private static volatile Validator instance = null;

    private PasswordValidator() {
    }

    public static Validator getInstance() {
        if (instance == null) {
            synchronized (PasswordValidator.class) {
                if (instance == null) {
                    instance = new PasswordValidator();
                }
            }
        }
        return instance;
    }

    @Override
    public Boolean isRequestValid(HttpServletRequest request) {
        messages = new HashMap<>();
        isValid = true;
        String oldPassword = request.getParameter("oldpassword");
        String password = request.getParameter("password");
        String rePassword = request.getParameter("repassword");

        if (isNull(oldPassword)) {
            isValid = false;
            messages.put("oldpassword", MESSAGE_NOT_NULL);
        }
        isValid = isPasswordValid(password, rePassword);
        request.setAttribute("messages", messages);
        return isValid;
    }
}
