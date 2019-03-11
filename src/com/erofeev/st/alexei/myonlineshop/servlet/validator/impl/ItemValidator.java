package com.erofeev.st.alexei.myonlineshop.servlet.validator.impl;

import com.erofeev.st.alexei.myonlineshop.servlet.validator.Validator;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class ItemValidator extends Validator {
    private static volatile Validator instance = null;

    private ItemValidator() {
    }

    public static Validator getInstance() {
        if (instance == null) {
            synchronized (ItemValidator.class) {
                if (instance == null) {
                    instance = new ItemValidator();
                }
            }
        }
        return instance;
    }

    public Boolean isRequestValid(HttpServletRequest request) {
        messages = new HashMap<>();
        isValid = true;
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String priceString = request.getParameter("price");
        if (isNull(name)) {
            isValid = false;
            messages.put("name", MESSAGE_NOT_NULL);
        }
        if (isNull(description)) {
            isValid = false;
            messages.put("description", MESSAGE_NOT_NULL);
        }
        if (isNull(priceString)) {
            isValid = false;
            messages.put("price", MESSAGE_NOT_NULL);
        } else {
            try {
                BigDecimal price = BigDecimal.valueOf(Double.valueOf(priceString));
                if (price.floatValue() <= 0) {
                    isValid = false;
                    messages.put("price", MESSAGE_PRICE);
                }
            } catch (NumberFormatException e) {
                isValid = false;
                messages.put("price", MESSAGE_PRICE);
            }
        }
        request.setAttribute("messages", messages);
        return isValid;
    }


}
