package com.erofeev.st.alexei.myonlineshop.service;

import com.erofeev.st.alexei.myonlineshop.repository.exception.ServiceException;

import java.io.File;

public interface DataBaseCreatorService {
    void createDataBaseFromFile(File file) throws ServiceException;

}
