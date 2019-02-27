package com.erofeev.st.alexei.myonlineshop.service;

import com.erofeev.st.alexei.myonlineshop.repository.exception.RepositoryException;
import com.erofeev.st.alexei.myonlineshop.repository.model.User;
import com.erofeev.st.alexei.myonlineshop.service.model.ProfileDTO;
import com.erofeev.st.alexei.myonlineshop.service.model.UserLoginDTO;
import com.erofeev.st.alexei.myonlineshop.service.model.UserRegistrationDTO;

public interface LoginRegistrationService {

    User loginUser(UserLoginDTO userLoginDTO);

    User registrationUser(UserRegistrationDTO userRegistrationDTO, ProfileDTO profileDTO) throws RepositoryException;
}
