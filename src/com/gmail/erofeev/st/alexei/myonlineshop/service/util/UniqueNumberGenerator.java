package com.gmail.erofeev.st.alexei.myonlineshop.service.util;

import java.util.UUID;

public class UniqueNumberGenerator {

    public static String generateUniqueNumber(){
        return UUID.randomUUID().toString();
    }
}
