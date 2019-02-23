package com.erofeev.st.alexei.myonlineshop.service;

import com.erofeev.st.alexei.myonlineshop.repository.model.User;

import java.util.List;

public interface UserService {
    User findById(Long id, boolean isLazy);

    List<User> findAllUser(boolean isLazy);
}
