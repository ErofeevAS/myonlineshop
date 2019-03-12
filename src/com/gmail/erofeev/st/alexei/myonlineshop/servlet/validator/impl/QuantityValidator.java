package com.gmail.erofeev.st.alexei.myonlineshop.servlet.validator.impl;

import com.gmail.erofeev.st.alexei.myonlineshop.servlet.validator.Validator;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

public class QuantityValidator extends Validator {
    private static volatile Validator instance = null;

    private QuantityValidator() {
    }

    public static Validator getInstance() {
        if (instance == null) {
            synchronized (QuantityValidator.class) {
                if (instance == null) {
                    instance = new QuantityValidator();
                }
            }
        }
        return instance;
    }

    @Override
    public Boolean isRequestValid(HttpServletRequest request) {
        messages = new HashMap<>();
        isValid = true;
        String quantity = request.getParameter("quantity");
        if (isNull(quantity)) {
            isValid = false;
            messages.put("quantity", MESSAGE_NOT_NULL);
        } else {
            try {
                Integer quantityInt = Integer.valueOf(quantity);
                if (quantityInt < 1) {
                    isValid = false;
                    messages.put("quantity", MESSAGE_QUANTITY);
                }
            } catch (NumberFormatException e) {
                isValid = false;
                messages.put("quantity", MESSAGE_QUANTITY);
            }
        }
        request.setAttribute("messages", messages);
        return isValid;
    }
}
