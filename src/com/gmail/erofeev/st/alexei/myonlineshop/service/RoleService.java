package com.gmail.erofeev.st.alexei.myonlineshop.service;

import com.gmail.erofeev.st.alexei.myonlineshop.repository.exception.ServiceException;
import com.gmail.erofeev.st.alexei.myonlineshop.repository.model.Role;

public interface RoleService {

    Role findRoleNyName(String name) throws ServiceException;
}
