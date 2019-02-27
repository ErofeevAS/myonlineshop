package com.erofeev.st.alexei.myonlineshop.app;

import com.erofeev.st.alexei.myonlineshop.repository.ProfileRepository;
import com.erofeev.st.alexei.myonlineshop.repository.exception.RepositoryException;
import com.erofeev.st.alexei.myonlineshop.repository.exception.ServiceException;
import com.erofeev.st.alexei.myonlineshop.repository.impl.ProfileRepositoryImpl;
import com.erofeev.st.alexei.myonlineshop.repository.model.Item;
import com.erofeev.st.alexei.myonlineshop.repository.model.Profile;
import com.erofeev.st.alexei.myonlineshop.service.*;
import com.erofeev.st.alexei.myonlineshop.service.converter.UserConverter;
import com.erofeev.st.alexei.myonlineshop.service.impl.*;
import com.erofeev.st.alexei.myonlineshop.repository.model.User;
import com.erofeev.st.alexei.myonlineshop.service.model.ProfileDTO;
import com.erofeev.st.alexei.myonlineshop.service.model.UserDTO;
import com.erofeev.st.alexei.myonlineshop.service.model.UserLoginDTO;
import com.erofeev.st.alexei.myonlineshop.service.model.UserRegistrationDTO;

import java.io.File;

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

        UserLoginDTO userLoginDTO = new UserLoginDTO("spokeman152@gmail.com", "1234");
        System.out.println(loginRegistrationService.loginUser(userLoginDTO));


        UserDTO userDTO = userService.findById(1L, false);
        System.out.println(userDTO);
        Item item = itemService.findById(13L);
        User user1 = UserConverter.fromDTO(userDTO);
        orderService.create(user1, item, 2);

        UserRegistrationDTO userRegistrationDTO = new UserRegistrationDTO("new@gmail.com", "1234", "1234", "john", "smith", "seller");
        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setAddress("Minsk sq. Victory 54 app 2");
        profileDTO.setTelephone("+375296661313");

        User regUser = loginRegistrationService.registrationUser(userRegistrationDTO, profileDTO);

        System.out.println("old password:" + userDTO.getPassword());
        userDTO.setPassword("1");
        userDTO.setFirstName("ALEKSEY");
        try {
            userService.update(userDTO);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        System.out.println("new password:" + userDTO.getPassword());
        profileDTO.setTelephone("+375291111111");
        profileDTO.setId(regUser.getId());
        profileService.update(profileDTO);


    }

}
