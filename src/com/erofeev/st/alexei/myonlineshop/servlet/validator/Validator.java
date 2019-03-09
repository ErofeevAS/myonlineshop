package com.erofeev.st.alexei.myonlineshop.servlet.validator;

import javax.servlet.http.HttpServletRequest;

public interface Validator {

    Boolean isRequestValid(HttpServletRequest request);
}
