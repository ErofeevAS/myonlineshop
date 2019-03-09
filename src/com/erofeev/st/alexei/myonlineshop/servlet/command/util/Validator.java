package com.erofeev.st.alexei.myonlineshop.servlet.command.util;

public class Validator {
    public final static Integer DEFAULT_PAGE_NUMBER = 1;
    public final static Integer DEFAULT_AMOUNT = 10;
    public final static Integer DEFAULT_MAX_AMOUNT = 100;

    public static Integer getMaxPage(Integer amountOfItems, Integer amountOnPage) {
        Integer maxPages = null;
        if (amountOfItems % amountOnPage == 0) {
            maxPages = amountOfItems / amountOnPage;
        } else {
            maxPages = amountOfItems / amountOnPage + 1;
        }
        return maxPages;
    }
}
