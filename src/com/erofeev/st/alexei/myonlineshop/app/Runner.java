package com.erofeev.st.alexei.myonlineshop.app;

import com.erofeev.st.alexei.myonlineshop.service.UserService;
import com.erofeev.st.alexei.myonlineshop.service.impl.UserServiceImpl;
import com.erofeev.st.alexei.myonlineshop.repository.model.User;

import java.util.List;

public class Runner {
    public static void main(String[] args) {
        UserService userService = UserServiceImpl.getInstance();
        Long userId = 1L;
        User user = userService.findById(userId,true);
        if (user != null) {
            System.out.println(user);
        } else {
            System.out.println("user not found");
        }
        user = userService.findById(userId,false);
        if (user != null) {
            System.out.println(user);
        } else {
            System.out.println("user not found");
        }
        System.out.println("ALL USERS:");
        List<User> users = userService.findAllUser(false);
        for (User user1 : users) {
            System.out.println(user1);
        }


    }

}
