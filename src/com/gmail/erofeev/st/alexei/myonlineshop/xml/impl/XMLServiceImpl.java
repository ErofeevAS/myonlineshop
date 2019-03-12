package com.gmail.erofeev.st.alexei.myonlineshop.xml.impl;

import com.gmail.erofeev.st.alexei.myonlineshop.repository.exception.ServiceException;
import com.gmail.erofeev.st.alexei.myonlineshop.service.util.XMLValidatorUtil;
import com.gmail.erofeev.st.alexei.myonlineshop.xml.XMLService;
import com.gmail.erofeev.st.alexei.myonlineshop.xml.model.ItemXML;
import com.gmail.erofeev.st.alexei.myonlineshop.xml.model.ItemsXML;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.InputStream;
import java.util.List;

public class XMLServiceImpl implements XMLService {
    private static volatile XMLService instance = null;

    private XMLServiceImpl() {
    }

    public static XMLService getInstance() {
        if (instance == null) {
            synchronized (XMLService.class) {
                if (instance == null) {
                    instance = new XMLServiceImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public List<ItemXML> importItemsFromFile(InputStream inputStream, File xsd) throws ServiceException {
        JAXBContext jaxbContext;
        try {
            jaxbContext = JAXBContext.newInstance(ItemsXML.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            ItemsXML itemsXML = (ItemsXML) jaxbUnmarshaller.unmarshal(inputStream);
            return itemsXML.getItem();
        } catch (JAXBException e) {
            String message = "Can't import from xml: " + e.getMessage();
            throw new ServiceException(message, e);
        }
    }

    @Override
    public Boolean isValidXML(InputStream inputStream, File xsd) {
        return (XMLValidatorUtil.validateAgainstXSD(inputStream, xsd));
    }

}



