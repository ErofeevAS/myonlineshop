package com.erofeev.st.alexei.myonlineshop.servlet.validator.impl;

import com.erofeev.st.alexei.myonlineshop.servlet.validator.Validator;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrationValidatorImpl implements Validator {
    private static volatile Validator instance = null;

    private RegistrationValidatorImpl() {
    }

    public static com.erofeev.st.alexei.myonlineshop.servlet.validator.Validator getInstance() {
        if (instance == null) {
            synchronized (RegistrationValidatorImpl.class) {
                if (instance == null) {
                    instance = new RegistrationValidatorImpl();
                }
            }
        }
        return instance;
    }

    private final  Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    private final  Pattern VALID_TELEPHONE_REGEX =
            Pattern.compile("\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}", Pattern.CASE_INSENSITIVE);

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

    private boolean isPasswordsEqual(String password, String repassword) {
        return password.equals(repassword);
    }


    private boolean isTelephoneValid(String email) {
        Matcher matcher = VALID_TELEPHONE_REGEX.matcher(email);
        return matcher.find();
    }

    public Boolean isRequestValid(HttpServletRequest request) {
        boolean isValid = true;
        Map<String, String> messages = new HashMap<>();
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String rePassword = request.getParameter("repassword");
        String firstName = request.getParameter("firstname");
        String lastName = request.getParameter("lastname");
        String address = request.getParameter("address");
        String telephone = request.getParameter("telephone");
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
        if (isNull(rePassword)) {
            messages.put("repassword", "repassword must be not empty");
            isValid = false;
        }
        if (!(isNull(password) && isNull(rePassword))) {
            if (!isPasswordsEqual(password, rePassword)) {
                messages.put("password", "password and repassword different");
                messages.put("repassword", "password and repassword different");
            }
        }
        if (isNull(firstName)) {
            messages.put("firstname", "firstName must be not empty");
            isValid = false;
        }
        if (isNull(lastName)) {
            messages.put("lastname", "lastName must be not empty");
            isValid = false;
        }
        if (isNull(address)) {
            messages.put("address", "address must be not empty");
            isValid = false;
        }
        if (isNull(telephone)) {
            messages.put("telephone", "telephone must be not empty");
            isValid = false;
        } else {
            if (!isTelephoneValid(telephone)) {
                messages.put("telephone", "telephone must be number format: \n 123-456-7890 or 123-456-7890 \n (123)456-7890 or (123)4567890");
                isValid = false;
            }
        }
        request.setAttribute("messages", messages);
        return isValid;
    }
}
