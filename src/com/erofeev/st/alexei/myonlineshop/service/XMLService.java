package com.erofeev.st.alexei.myonlineshop.service;

import com.erofeev.st.alexei.myonlineshop.service.model.ItemXML;

import java.io.File;
import java.util.List;

public interface XMLService {
    List<ItemXML> importItemsFromFile(File xml, File xsd);

}
