package com.erofeev.st.alexei.myonlineshop.servlet.validator.impl;

import com.erofeev.st.alexei.myonlineshop.repository.model.Profile;
import com.erofeev.st.alexei.myonlineshop.service.model.ProfileDTO;
import com.erofeev.st.alexei.myonlineshop.service.model.UserSessionDTO;
import com.erofeev.st.alexei.myonlineshop.servlet.validator.Validator;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProfileValidator implements Validator {
    private static volatile Validator instance;
    private final Pattern VALID_TELEPHONE_REGEX =
            Pattern.compile("\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}", Pattern.CASE_INSENSITIVE);

    private ProfileValidator() {
    }

    public static Validator getInstance() {
        if (instance == null) {
            synchronized (ProfileValidator.class) {
                if (instance == null) {
                    instance = new ProfileValidator();
                }
            }
        }
        return instance;
    }

    private boolean isNull(String text) {
        if ("".equals(text) || (text == null)) {
            return true;
        }
        return false;
    }

    private boolean isTelephoneValid(String email) {
        Matcher matcher = VALID_TELEPHONE_REGEX.matcher(email);
        return matcher.find();
    }

    @Override
    public Boolean isRequestValid(HttpServletRequest request) {
        boolean isValid = true;
        Map<String, String> messages = new HashMap<>();

        ProfileDTO profileDTO = (ProfileDTO) request.getAttribute("profile");
        String address = profileDTO.getAddress();
        String telephone = profileDTO.getTelephone();

        if (isNull(address)) {
            messages.put("address", "must be not null");
            isValid = false;
        }
        if (isNull(telephone)) {
            messages.put("telephone", "must be not null");
            isValid = false;
        }else{
            if(!isTelephoneValid(telephone)){
                messages.put("telephone", "telephone must be number format: \n 123-456-7890 or 123-456-7890 \n (123)456-7890 or (123)4567890");
                isValid = false;
            }
        }

        UserSessionDTO user = (UserSessionDTO) request.getAttribute("user");
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        if (isNull(firstName)) {
            messages.put("firstName", "must be not null");
            isValid = false;
        }
        if (isNull(lastName)) {
            messages.put("lastName", "must be not null");
            isValid = false;
        }
        request.setAttribute("messages",messages);
        return isValid;
    }
}
