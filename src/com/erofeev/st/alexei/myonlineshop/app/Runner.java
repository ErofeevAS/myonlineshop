package com.erofeev.st.alexei.myonlineshop.app;

import com.erofeev.st.alexei.myonlineshop.repository.model.Item;
import com.erofeev.st.alexei.myonlineshop.service.*;
import com.erofeev.st.alexei.myonlineshop.service.impl.*;
import com.erofeev.st.alexei.myonlineshop.repository.model.User;
import com.erofeev.st.alexei.myonlineshop.service.model.UserLoginDTO;

import java.io.File;
import java.util.List;

public class Runner {
    public static void main(String[] args) {
        File fileCreator = new File("src/com/erofeev/st/alexei/myonlineshop/sql/myshop.sql");
        File filePopulator = new File("src/com/erofeev/st/alexei/myonlineshop/sql/populate_table.sql");
        ItemService itemService = ItemServiceImpl.getInstance();
        DataBaseCreatorService dataBaseCreatorService = DataBaseCreatorServiceImpl.getInstance();
        LoginRegistrationService loginRegistrationService = LoginRegistrationServiceImpl.getInstance();
        UserService userService = UserServiceImpl.getInstance();
        OrderService orderService = OrderServiceImpl.getInstance();
//
//        dataBaseCreatorService.createDataBaseFromFile(fileCreator);
//        System.out.println("Start populate database:");
//        dataBaseCreatorService.createDataBaseFromFile(filePopulator);
//
//        File xml = new File("items.xml");
//        File xsd = new File("items.xsd");//
//        itemService.importFromXml(xml, xsd);

        UserLoginDTO userLoginDTO = new UserLoginDTO("spokeman152@gmail.com", "1234");
        System.out.println(loginRegistrationService.loginUser(userLoginDTO));


        User user = userService.findById(1L, false);
        System.out.println(user);
        Item item = itemService.findById(13L);
        orderService.create(user,item,2);



    }

}
