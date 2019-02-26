package com.erofeev.st.alexei.myonlineshop.service;

import com.erofeev.st.alexei.myonlineshop.repository.exception.RepositoryException;
import com.erofeev.st.alexei.myonlineshop.service.model.UserLoginDTO;
import com.erofeev.st.alexei.myonlineshop.service.model.UserRegistrationDTO;

public interface LoginRegistrationService {

    Boolean loginUser(UserLoginDTO userLoginDTO);

    Boolean registrationUser(UserRegistrationDTO userRegistrationDTO) throws RepositoryException;
}
