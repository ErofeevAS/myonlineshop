package com.erofeev.st.alexei.myonlineshop.service;

import com.erofeev.st.alexei.myonlineshop.repository.exception.RepositoryException;
import com.erofeev.st.alexei.myonlineshop.repository.exception.ServiceException;
import com.erofeev.st.alexei.myonlineshop.repository.model.Role;
import com.erofeev.st.alexei.myonlineshop.service.model.ProfileDTO;
import com.erofeev.st.alexei.myonlineshop.service.model.UserRegistrationDTO;

public interface LoginRegistrationService {

    void registrationUser(UserRegistrationDTO userRegistrationDTO, ProfileDTO profileDTO, Role role) throws RepositoryException, ServiceException;
}
