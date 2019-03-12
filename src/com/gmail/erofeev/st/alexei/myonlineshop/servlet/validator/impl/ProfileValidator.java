package com.gmail.erofeev.st.alexei.myonlineshop.servlet.validator.impl;

import com.gmail.erofeev.st.alexei.myonlineshop.service.model.ProfileDTO;
import com.gmail.erofeev.st.alexei.myonlineshop.service.model.UserSessionDTO;
import com.gmail.erofeev.st.alexei.myonlineshop.servlet.validator.Validator;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

public class ProfileValidator extends Validator {
    private static volatile Validator instance;

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


    @Override
    public Boolean isRequestValid(HttpServletRequest request) {
        messages = new HashMap<>();
        isValid = true;
        ProfileDTO profileDTO = (ProfileDTO) request.getAttribute("profile");
        if (profileDTO != null) {
            String address = profileDTO.getAddress();
            String telephone = profileDTO.getTelephone();
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
        }
        UserSessionDTO user = (UserSessionDTO) request.getSession().getAttribute("user");
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        if (isNull(firstName)) {
            messages.put("firstName", MESSAGE_NOT_NULL);
            isValid = false;
        }
        if (isNull(lastName)) {
            messages.put("lastName", MESSAGE_NOT_NULL);
            isValid = false;
        }
        request.setAttribute("messages", messages);
        return isValid;
    }
}