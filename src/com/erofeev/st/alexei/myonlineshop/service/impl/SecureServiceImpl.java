package com.erofeev.st.alexei.myonlineshop.service.impl;

import com.erofeev.st.alexei.myonlineshop.service.SecureService;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SecureServiceImpl implements SecureService {
    private static volatile SecureService instance = null;

    private SecureServiceImpl() {
    }

    public static SecureService getInstance() {
        if (instance == null) {
            synchronized (SecureService.class) {
                if (instance == null) {
                    instance = new SecureServiceImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public String hashPassword(String password) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.getMessage();
            e.printStackTrace();
        }
        return generatedPassword;
    }
}
