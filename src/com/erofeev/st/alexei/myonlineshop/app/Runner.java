package com.erofeev.st.alexei.myonlineshop.app;

import com.erofeev.st.alexei.myonlineshop.service.DataBaseCreatorService;
import com.erofeev.st.alexei.myonlineshop.service.ItemService;
import com.erofeev.st.alexei.myonlineshop.service.UserService;
import com.erofeev.st.alexei.myonlineshop.service.XMLService;
import com.erofeev.st.alexei.myonlineshop.service.impl.DataBaseCreatorServiceImpl;
import com.erofeev.st.alexei.myonlineshop.service.impl.ItemServiceImpl;
import com.erofeev.st.alexei.myonlineshop.service.impl.UserServiceImpl;
import com.erofeev.st.alexei.myonlineshop.repository.model.User;
import com.erofeev.st.alexei.myonlineshop.service.impl.XMLServiceImpl;
import com.erofeev.st.alexei.myonlineshop.service.model.ItemXML;

import java.io.File;
import java.util.List;

public class Runner {
    public static void main(String[] args) {
        File fileCreator = new File("src/com/erofeev/st/alexei/myonlineshop/sql/myshop.sql");
        File filePopulator = new File("src/com/erofeev/st/alexei/myonlineshop/sql/populate_table.sql");

//        DataBaseCreatorService dataBaseCreatorService = DataBaseCreatorServiceImpl.getInstance();
//        dataBaseCreatorService.createDataBaseFromFile(fileCreator);
//        System.out.println("Start populate database:");
//        dataBaseCreatorService.createDataBaseFromFile(filePopulator);

        File xml = new File("items.xml");
        File xsd = new File("items.xsd");
        ItemService itemService = ItemServiceImpl.getInstance();
        itemService.importFromXml(xml, xsd);

    }

}
