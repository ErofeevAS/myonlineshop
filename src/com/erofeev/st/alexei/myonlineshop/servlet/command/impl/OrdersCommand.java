package com.erofeev.st.alexei.myonlineshop.servlet.command.impl;

import com.erofeev.st.alexei.myonlineshop.config.connection.ConfigurationManagerImpl;
import com.erofeev.st.alexei.myonlineshop.repository.model.enums.StatusEnum;
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
        String idString = request.getParameter("id");
        List<OrderDTO> orderDTOList = orderService.showAllOrders(1, 10);
        request.setAttribute("orders", orderDTOList);
        if (idString == null) {
            return page;
        }
        Long id = Long.valueOf(idString);
        String statusString = request.getParameter("status");
        StatusEnum status = StatusEnum.valueOf(statusString);
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(id);
        orderService.changeStatus(orderDTO,status);

        return page;
    }
}
