package com.erofeev.st.alexei.myonlineshop.app;

import com.erofeev.st.alexei.myonlineshop.service.*;
import com.erofeev.st.alexei.myonlineshop.service.impl.*;
import com.erofeev.st.alexei.myonlineshop.repository.model.User;
import com.erofeev.st.alexei.myonlineshop.service.model.ItemXML;
import com.erofeev.st.alexei.myonlineshop.service.model.UserLoginDTO;
import com.erofeev.st.alexei.myonlineshop.service.model.UserRegistrationDTO;

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

//        File xml = new File("items.xml");
//        File xsd = new File("items.xsd");
//        ItemService itemService = ItemServiceImpl.getInstance();
//        itemService.importFromXml(xml, xsd);

        UserLoginDTO userLoginDTO = new UserLoginDTO("spokeman152@gmail.com", "1234");
        LoginRegistrationService loginRegistrationService = LoginRegistrationServiceImpl.getInstance();
//        System.out.println(loginRegistrationService.loginUser(userLoginDTO));
        UserRegistrationDTO userRegistrationDTO = new UserRegistrationDTO("nagibator666@gmail.com", "123", "123", "Vaska", "Pupkin");
        loginRegistrationService.registrationUser(userRegistrationDTO);
    }

}
