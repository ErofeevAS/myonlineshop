package com.erofeev.st.alexei.myonlineshop.servlet.validator;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Validator {

    public abstract Boolean isRequestValid(HttpServletRequest request);

    protected final String MESSAGE_NOT_NULL = "must be not empty";
    protected final String MESSAGE_DIFFERENT_PASSWORD = "password and repassword different";
    protected final String MESSAGE_TELEPHONE_FORMAT = "telephone must be number format: \n 123-456-7890 or 123-456-7890 \n (123)456-7890 or (123)4567890";
    protected final String MESSAGE_EMAIL_FORMAT = "wrong format for email";
    protected final String MESSAGE_PRICE = "must be number more than 0";
    protected final String MESSAGE_QUANTITY = "must be more than zero";

    private final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    private final Pattern VALID_TELEPHONE_REGEX =
            Pattern.compile("\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}", Pattern.CASE_INSENSITIVE);

    protected boolean isEmailValid(String email) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.find();
    }

    protected boolean isTelephoneValid(String email) {
        Matcher matcher = VALID_TELEPHONE_REGEX.matcher(email);
        return matcher.find();
    }

    protected boolean isNull(String text) {
        return "".equals(text) || (text == null);
    }

    protected boolean isPasswordsEqual(String password, String repassword) {
        return password.equals(repassword);
    }


}
