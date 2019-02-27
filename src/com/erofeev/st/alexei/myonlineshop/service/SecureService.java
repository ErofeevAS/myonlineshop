package com.erofeev.st.alexei.myonlineshop.service;

public interface SecureService {
    String hashPassword(String password);

    Boolean comparePasswords(String hashOne, String hashTwo);
}
