package com.erofeev.st.alexei.myonlineshop.service;

import com.erofeev.st.alexei.myonlineshop.repository.exception.ServiceException;
import com.erofeev.st.alexei.myonlineshop.service.model.ProfileDTO;

public interface ProfileService {

    void save(ProfileDTO profileDTO);

    Integer update(ProfileDTO profileDTO) throws ServiceException;

}
