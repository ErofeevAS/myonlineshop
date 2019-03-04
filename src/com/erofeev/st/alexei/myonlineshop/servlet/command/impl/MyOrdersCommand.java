package com.erofeev.st.alexei.myonlineshop.servlet.command.impl;

import com.erofeev.st.alexei.myonlineshop.config.connection.ConfigurationManagerImpl;
import com.erofeev.st.alexei.myonlineshop.service.OrderService;
import com.erofeev.st.alexei.myonlineshop.service.impl.OrderServiceImpl;
import com.erofeev.st.alexei.myonlineshop.service.model.OrderDTO;
import com.erofeev.st.alexei.myonlineshop.service.model.UserDTO;
import com.erofeev.st.alexei.myonlineshop.servlet.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class MyOrdersCommand implements Command {
    private OrderService orderService = OrderServiceImpl.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = ConfigurationManagerImpl.getInstance().getProperty(ConfigurationManagerImpl.MYORDERS_PAGE_PATH);
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        List<OrderDTO> orderDTOList = orderService.showUserOrders(userDTO);
        request.setAttribute("orders",orderDTOList);
        return page;
    }
}