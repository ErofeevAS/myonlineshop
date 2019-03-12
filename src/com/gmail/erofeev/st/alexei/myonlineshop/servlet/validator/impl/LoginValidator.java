package com.gmail.erofeev.st.alexei.myonlineshop.servlet.validator.impl;

import com.gmail.erofeev.st.alexei.myonlineshop.servlet.validator.Validator;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

public class LoginValidator extends Validator {
    private static volatile Validator instance = null;

    private LoginValidator() {
    }

    public static Validator getInstance() {
        if (instance == null) {
            synchronized (LoginValidator.class) {
                if (instance == null) {
                    instance = new LoginValidator();
                }
            }
        }
        return instance;
    }

    public Boolean isRequestValid(HttpServletRequest request) {
        messages = new HashMap<>();
        isValid = true;
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        if (isNull(email)) {
            messages.put("email", MESSAGE_NOT_NULL);
            isValid = false;

        } else {
            if (!isEmailValid(email)) {
                messages.put("email", MESSAGE_EMAIL_FORMAT);
                isValid = false;
            }
        }
        if (isNull(password)) {
            messages.put("password", MESSAGE_NOT_NULL);
            isValid = false;
        }
        request.setAttribute("messages", messages);
        return isValid;
    }
}
