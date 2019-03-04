package com.erofeev.st.alexei.myonlineshop.service;

import com.erofeev.st.alexei.myonlineshop.repository.exception.RepositoryException;
import com.erofeev.st.alexei.myonlineshop.repository.exception.ServiceException;
import com.erofeev.st.alexei.myonlineshop.repository.model.Item;
import com.erofeev.st.alexei.myonlineshop.service.model.ItemDTO;

import java.io.File;
import java.util.List;

public interface ItemService {

    List<ItemDTO> findItems(int pageNumber, int amount);

    void save(ItemDTO itemDTO) throws ServiceException;

    void delete(ItemDTO itemDTO) throws ServiceException;

    ItemDTO findById(Long id) throws ServiceException, RepositoryException;

    ItemDTO findByUniqueNumber(String uniqueNumber) throws RepositoryException, ServiceException;

    Boolean importFromXml(File xml,File xsd);

    Integer getAmountOfItems();

}
