package com.erofeev.st.alexei.myonlineshop.servlet.command.impl;

import com.erofeev.st.alexei.myonlineshop.config.connection.ConfigurationManagerImpl;
import com.erofeev.st.alexei.myonlineshop.repository.exception.RepositoryException;
import com.erofeev.st.alexei.myonlineshop.repository.exception.ServiceException;
import com.erofeev.st.alexei.myonlineshop.service.ItemService;
import com.erofeev.st.alexei.myonlineshop.service.OrderService;
import com.erofeev.st.alexei.myonlineshop.service.impl.ItemServiceImpl;
import com.erofeev.st.alexei.myonlineshop.service.impl.OrderServiceImpl;
import com.erofeev.st.alexei.myonlineshop.service.model.ItemDTO;
import com.erofeev.st.alexei.myonlineshop.service.model.OrderDTO;
import com.erofeev.st.alexei.myonlineshop.service.model.UserDTO;
import com.erofeev.st.alexei.myonlineshop.service.model.UserSessionDTO;
import com.erofeev.st.alexei.myonlineshop.servlet.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class OrderCommand implements Command {
    private ItemService itemService = ItemServiceImpl.getInstance();
    private OrderService orderService = OrderServiceImpl.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = ConfigurationManagerImpl.getInstance().getProperty(ConfigurationManagerImpl.ITEMS_PAGE);

        String quantityString = request.getParameter("quantity");
        if ((quantityString == null) || ("".equals(quantityString))) {
            page = ConfigurationManagerImpl.getInstance().getProperty(ConfigurationManagerImpl.ITEMS_PAGE);
            request.setAttribute("error", "quantity must be not empty");
            String baseURL = "/myonlineshop/shop?command=";
            try {
                response.sendRedirect(baseURL+"items");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return page;
        }
        Integer quantity = Integer.valueOf(quantityString);
        String uniqueNumber = request.getParameter("uniquenumber");
        UserDTO userDTO = new UserDTO();
        UserSessionDTO userSessionDTO = (UserSessionDTO) request.getSession().getAttribute("user");
        userDTO.setId(userSessionDTO.getId());

        try {
            ItemDTO itemDTO = itemService.findByUniqueNumber(uniqueNumber);
            OrderDTO orderDTO = orderService.create(userDTO, itemDTO, quantity);
            orderDTO = orderService.findById(orderDTO.getId());
            String firstName = orderDTO.getFirstName();
            String lastName = orderDTO.getLastName();
            String itemName = orderDTO.getItemName();
            BigDecimal price = orderDTO.getPrice();
            Timestamp timestamp = orderDTO.getCreatedDate();
            quantity = orderDTO.getQuantity();

            request.setAttribute("firstname", firstName);
            request.setAttribute("lastname", lastName);
            request.setAttribute("itemname", itemName);
            request.setAttribute("price", price);
            request.setAttribute("timestamp", timestamp);
            request.setAttribute("quantity", quantity);

            page = ConfigurationManagerImpl.getInstance().getProperty(ConfigurationManagerImpl.ORDER_PAGE);

            System.out.println(orderDTO);

        } catch (RepositoryException e) {
            e.printStackTrace();
        } catch (ServiceException e) {
            e.printStackTrace();
        }

        return page;
    }
}
