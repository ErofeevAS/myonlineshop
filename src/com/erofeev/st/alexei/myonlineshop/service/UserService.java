package com.erofeev.st.alexei.myonlineshop.service;

import com.erofeev.st.alexei.myonlineshop.repository.exception.ServiceException;
import com.erofeev.st.alexei.myonlineshop.service.model.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO findById(Long id, boolean isLazy);

    Integer update(UserDTO userDTO) throws ServiceException;


}
