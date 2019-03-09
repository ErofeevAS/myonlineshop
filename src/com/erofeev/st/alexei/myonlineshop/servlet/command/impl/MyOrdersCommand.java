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
import com.erofeev.st.alexei.myonlineshop.servlet.command.util.Validator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static com.erofeev.st.alexei.myonlineshop.servlet.command.util.Validator.*;

public class MyOrdersCommand implements Command {
    private OrderService orderService = OrderServiceImpl.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String url = ConfigurationManagerImpl.getInstance().getProperty(ConfigurationManagerImpl.MY_ORDERS_PAGE);
        String pageString = request.getParameter("page");
        String amountString = request.getParameter("amount");
        if ((pageString == null) || ("".equals(pageString))) {
            pageString = "1";
        }
        if ((amountString == null) || ("".equals(amountString))) {
            amountString = "5";
        }
        try {
            Integer page = Integer.valueOf(pageString);
            Integer amount = Integer.valueOf(amountString);
            Integer amountOfItems = orderService.getAmountOfOrders();
            Integer maxPages = Validator.getMaxPage(amountOfItems, amount);
            if ((page <= 1) || (page > maxPages)) {
                page = DEFAULT_PAGE_NUMBER;
            }
            if ((amount <= 1) || (amount > DEFAULT_MAX_AMOUNT)) {
                amount = DEFAULT_AMOUNT;
            }
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
