package com.erofeev.st.alexei.myonlineshop.servlet.validator.impl;

import com.erofeev.st.alexei.myonlineshop.servlet.validator.Validator;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class ItemValidator implements Validator {
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

    @Override
    public Boolean isRequestValid(HttpServletRequest request) {
        boolean isValid = true;
        Map<String, String> messages = new HashMap<>();
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String priceString = request.getParameter("price");
        if (isNull(name)) {
            isValid = false;
            messages.put("name", "must be not null");
        }
        if (isNull(description)) {
            isValid = false;
            messages.put("description", "must be not null");
        }
        if (isNull(priceString)) {
            isValid = false;
            messages.put("price", "must be not null");
        } else {
            try {
                BigDecimal price = BigDecimal.valueOf(Double.valueOf(priceString));
            } catch (NumberFormatException e) {
                isValid = false;
                messages.put("price", "price must be number");
            }
        }
        request.setAttribute("messages", messages);
        return isValid;
    }

    private boolean isNull(String text) {
        if ("".equals(text) || (text == null)) {
            return true;
        }
        return false;
    }
}
