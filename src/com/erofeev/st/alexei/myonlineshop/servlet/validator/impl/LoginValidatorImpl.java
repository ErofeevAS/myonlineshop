package com.erofeev.st.alexei.myonlineshop.servlet.validator.impl;

import com.erofeev.st.alexei.myonlineshop.servlet.validator.Validator;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginValidatorImpl implements Validator {
    private static volatile Validator instance = null;

    private LoginValidatorImpl() {
    }

    public static Validator getInstance() {
        if (instance == null) {
            synchronized (LoginValidatorImpl.class) {
                if (instance == null) {
                    instance = new LoginValidatorImpl();
                }
            }
        }
        return instance;
    }

    private final static Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    private boolean validateEmail(String email) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.find();
    }

    private boolean isNull(String text) {
        if ("".equals(text) || (text == null)) {
            return true;
        }
        return false;
    }

    public Boolean isRequestValid(HttpServletRequest request) {
        boolean isValid = true;
        Map<String, String> messages = new HashMap<>();
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        if (isNull(email)) {
            messages.put("email", "email must be not empty");
            isValid = false;

        } else {
            if (!validateEmail(email)) {
                messages.put("email", "wrong pattern for email");
                isValid = false;
            }
        }
        if (isNull(password)) {
            messages.put("password", "password must be not empty");
            isValid = false;
        }
        request.setAttribute("messages", messages);
        return isValid;
    }
}
