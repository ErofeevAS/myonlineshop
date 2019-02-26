package com.erofeev.st.alexei.myonlineshop.service;

import com.erofeev.st.alexei.myonlineshop.service.model.ProfileDTO;

public interface ProfileService {

    void save(ProfileDTO profileDTO);

    Boolean update(ProfileDTO profileDTO);

}
