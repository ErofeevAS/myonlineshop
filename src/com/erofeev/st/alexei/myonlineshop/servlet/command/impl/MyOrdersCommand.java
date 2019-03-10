package com.erofeev.st.alexei.myonlineshop.servlet.command.impl;

import com.erofeev.st.alexei.myonlineshop.config.connection.ConfigurationManagerImpl;
import com.erofeev.st.alexei.myonlineshop.repository.exception.ServiceException;
import com.erofeev.st.alexei.myonlineshop.service.OrderService;
import com.erofeev.st.alexei.myonlineshop.service.converter.UserConverter;
import com.erofeev.st.alexei.myonlineshop.service.impl.OrderServiceImpl;
import com.erofeev.st.alexei.myonlineshop.service.model.OrderDTO;
import com.erofeev.st.alexei.myonlineshop.service.model.UserDTO;
import com.erofeev.st.alexei.myonlineshop.service.model.UserSessionDTO;
import com.erofeev.st.alexei.myonlineshop.servlet.command.Command;
import com.erofeev.st.alexei.myonlineshop.servlet.validator.util.PaginatorUtil;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


public class MyOrdersCommand implements Command {
    private OrderService orderService = OrderServiceImpl.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String url = ConfigurationManagerImpl.getInstance().getProperty(ConfigurationManagerImpl.MY_ORDERS_PAGE);
        try {
            Integer amountOfItems = orderService.getAmountOfOrders();
            Integer amount = PaginatorUtil.getAmount(request);
            Integer maxPages = PaginatorUtil.getMaxPage(amountOfItems, amount);
            Integer page = PaginatorUtil.getPage(request, maxPages);
            UserSessionDTO user = (UserSessionDTO) request.getSession().getAttribute("user");
            UserDTO userDTO = UserConverter.fromSessionDTO(user);
            List<OrderDTO> orders = orderService.getUserOrders(userDTO, page, amount);
            request.setAttribute("page", page);
            request.setAttribute("amount", amount);
            request.setAttribute("orders", orders);
            request.setAttribute("maxpages", maxPages);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        return url;

    }
}
