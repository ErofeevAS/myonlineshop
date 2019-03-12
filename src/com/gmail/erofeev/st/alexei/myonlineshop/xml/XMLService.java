package com.gmail.erofeev.st.alexei.myonlineshop.xml;

import com.gmail.erofeev.st.alexei.myonlineshop.repository.exception.ServiceException;
import com.gmail.erofeev.st.alexei.myonlineshop.xml.model.ItemXML;

import java.io.File;
import java.io.InputStream;
import java.util.List;

public interface XMLService {
//    List<ItemXML> importItemsFromFile(File xml, File xsd);

    List<ItemXML> importItemsFromFile(InputStream inputStream, File xsd) throws ServiceException;

    Boolean isValidXML(InputStream inputStream, File xsd);

}