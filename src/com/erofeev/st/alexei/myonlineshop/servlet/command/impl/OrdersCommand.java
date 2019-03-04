package com.erofeev.st.alexei.myonlineshop.servlet.command.impl;

import com.erofeev.st.alexei.myonlineshop.config.connection.ConfigurationManagerImpl;
import com.erofeev.st.alexei.myonlineshop.service.OrderService;
import com.erofeev.st.alexei.myonlineshop.service.impl.OrderServiceImpl;
import com.erofeev.st.alexei.myonlineshop.service.model.OrderDTO;
import com.erofeev.st.alexei.myonlineshop.servlet.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class OrdersCommand implements Command {
    private OrderService orderService = OrderServiceImpl.getInstance();
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = ConfigurationManagerImpl.getInstance().getProperty(ConfigurationManagerImpl.ORDERS_PAGE_PATH);
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(1L);
        List<OrderDTO> orderDTOList = orderService.showAllOrders(1,10);
        request.setAttribute("orders",orderDTOList);
        return page;
    }
}
