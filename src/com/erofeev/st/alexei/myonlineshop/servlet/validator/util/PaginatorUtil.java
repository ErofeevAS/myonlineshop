package com.erofeev.st.alexei.myonlineshop.servlet.validator.util;

import javax.servlet.http.HttpServletRequest;

public class PaginatorUtil {
    public static Integer getPage(HttpServletRequest request, Integer maxPage) {
        final Integer DEFAULT_PAGE = 1;
        Integer pageInt = DEFAULT_PAGE;
        String page = request.getParameter("page");
        if (page == null || "".equals(page)) {

        } else {
            try {
                pageInt = Integer.valueOf(page);
                if (pageInt < 1 || pageInt > maxPage) {
                    pageInt = DEFAULT_PAGE;
                }
            } catch (NumberFormatException e) {
                pageInt = DEFAULT_PAGE;
            }
        }
        return pageInt;
    }

    public static Integer getAmount(HttpServletRequest request) {
        final Integer DEFAULT_AMOUNT = 10;
        Integer amountInt = DEFAULT_AMOUNT;
        String amount = request.getParameter("amount");
        if (amount == null || "".equals(amount)) {
        } else {
            try {
                amountInt = Integer.valueOf(amount);
                if (amountInt < 1) {
                    amountInt = DEFAULT_AMOUNT;
                }
            } catch (NumberFormatException e) {
                amountInt = DEFAULT_AMOUNT;
            }
        }
        return amountInt;
    }

    public static Integer getMaxPage(Integer amountOfItems, Integer amountOnPage) {
        Integer maxPages;
        if (amountOfItems < 1) {
            return 1;
        }
        if (amountOfItems % amountOnPage == 0) {
            maxPages = amountOfItems / amountOnPage;
        } else {
            maxPages = amountOfItems / amountOnPage + 1;
        }
        return maxPages;
    }
}
