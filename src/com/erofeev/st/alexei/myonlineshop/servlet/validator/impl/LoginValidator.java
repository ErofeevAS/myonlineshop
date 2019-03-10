package com.erofeev.st.alexei.myonlineshop.servlet.validator.impl;

import com.erofeev.st.alexei.myonlineshop.servlet.validator.Validator;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

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
        boolean isValid = true;
        Map<String, String> messages = new HashMap<>();
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
