package com.erofeev.st.alexei.myonlineshop.app;

import com.erofeev.st.alexei.myonlineshop.repository.ProfileRepository;
import com.erofeev.st.alexei.myonlineshop.repository.exception.RepositoryException;
import com.erofeev.st.alexei.myonlineshop.repository.exception.ServiceException;
import com.erofeev.st.alexei.myonlineshop.repository.impl.ProfileRepositoryImpl;
import com.erofeev.st.alexei.myonlineshop.repository.model.Item;
import com.erofeev.st.alexei.myonlineshop.repository.model.Profile;
import com.erofeev.st.alexei.myonlineshop.repository.model.enums.StatusEnum;
import com.erofeev.st.alexei.myonlineshop.service.*;
import com.erofeev.st.alexei.myonlineshop.service.converter.UserConverter;
import com.erofeev.st.alexei.myonlineshop.service.impl.*;
import com.erofeev.st.alexei.myonlineshop.repository.model.User;
import com.erofeev.st.alexei.myonlineshop.service.model.*;

import java.io.File;
import java.util.List;

public class Runner {
    public static void main(String[] args) throws ServiceException, RepositoryException {
//        File fileCreator = new File("src/com/erofeev/st/alexei/myonlineshop/sql/myshop.sql");
//        File filePopulator = new File("src/com/erofeev/st/alexei/myonlineshop/sql/populate_table.sql");
//        File fileCreator = new File("sql/myshop.sql");
//        File filePopulator = new File("sql/populate_table.sql");
//        ItemService itemService = ItemServiceImpl.getInstance();
//        DataBaseCreatorService dataBaseCreatorService = DataBaseCreatorServiceImpl.getInstance();
//        LoginRegistrationService loginRegistrationService = LoginRegistrationServiceImpl.getInstance();
//        UserService userService = UserServiceImpl.getInstance();
//        OrderService orderService = OrderServiceImpl.getInstance();
//        ProfileService profileService = ProfileServiceImpl.getInstance();
//
//        dataBaseCreatorService.createDataBaseFromFile(fileCreator);
//        System.out.println("Start populate database:");
//        dataBaseCreatorService.createDataBaseFromFile(filePopulator);
        initDataBase();


    }
    private static void initDataBase() {
        File fileCreator = new File("sql/myshop.sql");
        File filePopulator = new File("sql/populate_table.sql");
        DataBaseCreatorService dataBaseCreatorService = DataBaseCreatorServiceImpl.getInstance();
        System.out.println(fileCreator.exists());
        try {
            dataBaseCreatorService.createDataBaseFromFile(fileCreator);
            dataBaseCreatorService.createDataBaseFromFile(filePopulator);
//            isLoadedDataBase = true;
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }


}
