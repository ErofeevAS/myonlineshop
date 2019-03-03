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
        File fileCreator = new File("src/com/erofeev/st/alexei/myonlineshop/sql/myshop.sql");
        File filePopulator = new File("src/com/erofeev/st/alexei/myonlineshop/sql/populate_table.sql");
        ItemService itemService = ItemServiceImpl.getInstance();
        DataBaseCreatorService dataBaseCreatorService = DataBaseCreatorServiceImpl.getInstance();
        LoginRegistrationService loginRegistrationService = LoginRegistrationServiceImpl.getInstance();
        UserService userService = UserServiceImpl.getInstance();
        OrderService orderService = OrderServiceImpl.getInstance();
        ProfileService profileService = ProfileServiceImpl.getInstance();

        dataBaseCreatorService.createDataBaseFromFile(fileCreator);
        System.out.println("Start populate database:");
        dataBaseCreatorService.createDataBaseFromFile(filePopulator);

        File xml = new File("items.xml");
        File xsd = new File("items.xsd");
        itemService.importFromXml(xml, xsd);

        SecureService secureService = SecureServiceImpl.getInstance();
        System.out.println(secureService.hashPassword("1234"));
        UserLoginDTO userLoginDTO = new UserLoginDTO("sp@gmail.com", "1234");
        System.out.println("IS USER LOGIN? " + loginRegistrationService.loginUser(userLoginDTO));


        UserDTO userDTO = new UserDTO();
        ItemDTO itemDTO = new ItemDTO();
        userDTO.setId(1L);
        itemDTO.setId(10L);

        orderService.create(userDTO, itemDTO, 5);
        itemDTO.setId(1L);
        orderService.create(userDTO, itemDTO, 1);
        itemDTO.setId(2L);
        orderService.create(userDTO, itemDTO, 50);

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(1L);
        orderService.changeStatus(orderDTO, StatusEnum.DELIVERED);
        System.out.println(orderService.findById(1L));
        List<OrderDTO > orderDTOList = orderService.showUserOrders(userDTO);
        for (OrderDTO dto : orderDTOList) {
            System.out.println(dto);
        }
        System.out.println(itemService.getAmountOfItems());

    }

}
