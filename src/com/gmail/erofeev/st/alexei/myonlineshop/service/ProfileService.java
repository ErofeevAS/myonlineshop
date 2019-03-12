package com.gmail.erofeev.st.alexei.myonlineshop.service;

import com.gmail.erofeev.st.alexei.myonlineshop.repository.exception.ServiceException;
import com.gmail.erofeev.st.alexei.myonlineshop.service.model.ProfileDTO;

public interface ProfileService {


    void save(ProfileDTO profileDTO) throws ServiceException;

    Integer update(ProfileDTO profileDTO) throws ServiceException;

    ProfileDTO findById(Long id) throws ServiceException;



}
