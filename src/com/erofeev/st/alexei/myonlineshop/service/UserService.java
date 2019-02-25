package com.erofeev.st.alexei.myonlineshop.service;

import com.erofeev.st.alexei.myonlineshop.service.model.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO findById(Long id, boolean isLazy);


}
