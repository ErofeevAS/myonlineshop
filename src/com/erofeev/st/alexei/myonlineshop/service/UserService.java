package com.erofeev.st.alexei.myonlineshop.service;

import com.erofeev.st.alexei.myonlineshop.repository.exception.ServiceException;
import com.erofeev.st.alexei.myonlineshop.repository.model.User;
import com.erofeev.st.alexei.myonlineshop.service.model.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO findById(Long id, boolean isLazy) throws ServiceException;

    Integer updateInfo(UserDTO userDTO) throws ServiceException;

    void updatePassword(Long id, String oldPassword, String newPassword) throws ServiceException;

    User findByEmail(String email, boolean isLazy) throws ServiceException;

    Boolean isValidPassword(User user, String password);


}
