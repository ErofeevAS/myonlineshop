package com.erofeev.st.alexei.myonlineshop.xml.impl;

import com.erofeev.st.alexei.myonlineshop.xml.model.ItemXML;
import com.erofeev.st.alexei.myonlineshop.xml.model.ItemsXML;
import com.erofeev.st.alexei.myonlineshop.xml.XMLService;
import com.erofeev.st.alexei.myonlineshop.service.util.XMLValidatorUtil;

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
    public List<ItemXML> importItemsFromFile(File xml, File xsd) {

        if (XMLValidatorUtil.validateAgainstXSD(xml, xsd)) {
            System.out.println("XML file  valid!");
            JAXBContext jaxbContext;
            try {
                jaxbContext = JAXBContext.newInstance(ItemsXML.class);
                Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                ItemsXML itemsXML = (ItemsXML) jaxbUnmarshaller.unmarshal(xml);
                List<ItemXML> items = itemsXML.getItem();
                return items;

            } catch (JAXBException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("XML file " + xml + " not valid!");
        }
        return null;

    }

    @Override
    public List<ItemXML> importItemsFromFile(InputStream inputStream, File xsd) {
        if (XMLValidatorUtil.validateAgainstXSD(inputStream, xsd)) {
            System.out.println("XML file  valid!");
            JAXBContext jaxbContext;
            try {
                jaxbContext = JAXBContext.newInstance(ItemsXML.class);
                Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                ItemsXML itemsXML = (ItemsXML) jaxbUnmarshaller.unmarshal(inputStream);
                List<ItemXML> items = itemsXML.getItem();
                return items;

            } catch (JAXBException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("XML file not valid!");
        }
        return null;
    }

}



