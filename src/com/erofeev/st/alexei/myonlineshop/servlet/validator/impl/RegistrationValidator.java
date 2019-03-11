package com.erofeev.st.alexei.myonlineshop.servlet.validator.impl;

import com.erofeev.st.alexei.myonlineshop.servlet.validator.Validator;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

public class RegistrationValidator extends Validator {
    private static volatile Validator instance = null;

    private RegistrationValidator() {
    }

    public static com.erofeev.st.alexei.myonlineshop.servlet.validator.Validator getInstance() {
        if (instance == null) {
            synchronized (RegistrationValidator.class) {
                if (instance == null) {
                    instance = new RegistrationValidator();
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
        String rePassword = request.getParameter("repassword");
        String firstName = request.getParameter("firstname");
        String lastName = request.getParameter("lastname");
        String address = request.getParameter("address");
        String telephone = request.getParameter("telephone");
        if (isNull(email)) {
            messages.put("email", MESSAGE_NOT_NULL);
            isValid = false;
        } else {
            if (!isEmailValid(email)) {
                messages.put("email", MESSAGE_EMAIL_FORMAT);
                isValid = false;
            }
        }
        isValid = isPasswordValid(password, rePassword);
        if (isNull(firstName)) {
            messages.put("firstname", MESSAGE_NOT_NULL);
            isValid = false;
        }
        if (isNull(lastName)) {
            messages.put("lastname", MESSAGE_NOT_NULL);
            isValid = false;
        }
        if (isNull(address)) {
            messages.put("address", MESSAGE_NOT_NULL);
            isValid = false;
        }
        if (isNull(telephone)) {
            messages.put("telephone", MESSAGE_NOT_NULL);
            isValid = false;
        } else {
            if (!isTelephoneValid(telephone)) {
                messages.put("telephone", MESSAGE_TELEPHONE_FORMAT);
                isValid = false;
            }
        }
        request.setAttribute("messages", messages);
        return isValid;
    }
}
