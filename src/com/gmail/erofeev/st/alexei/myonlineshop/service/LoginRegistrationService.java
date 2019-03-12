package com.gmail.erofeev.st.alexei.myonlineshop.service;

import com.gmail.erofeev.st.alexei.myonlineshop.repository.exception.RepositoryException;
import com.gmail.erofeev.st.alexei.myonlineshop.repository.exception.ServiceException;
import com.gmail.erofeev.st.alexei.myonlineshop.repository.model.Role;
import com.gmail.erofeev.st.alexei.myonlineshop.service.model.ProfileDTO;
import com.gmail.erofeev.st.alexei.myonlineshop.service.model.UserRegistrationDTO;

public interface LoginRegistrationService {

    void registrationUser(UserRegistrationDTO userRegistrationDTO, ProfileDTO profileDTO, Role role) throws RepositoryException, ServiceException;
}
