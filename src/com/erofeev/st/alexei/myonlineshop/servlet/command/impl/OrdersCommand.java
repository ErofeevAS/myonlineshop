package com.erofeev.st.alexei.myonlineshop.servlet.command.impl;

import com.erofeev.st.alexei.myonlineshop.config.connection.ConfigurationManagerImpl;
import com.erofeev.st.alexei.myonlineshop.repository.exception.RepositoryException;
import com.erofeev.st.alexei.myonlineshop.repository.exception.ServiceException;
import com.erofeev.st.alexei.myonlineshop.repository.model.enums.StatusEnum;
import com.erofeev.st.alexei.myonlineshop.service.OrderService;
import com.erofeev.st.alexei.myonlineshop.service.impl.OrderServiceImpl;
import com.erofeev.st.alexei.myonlineshop.service.model.ItemDTO;
import com.erofeev.st.alexei.myonlineshop.service.model.OrderDTO;
import com.erofeev.st.alexei.myonlineshop.servlet.command.Command;
import com.erofeev.st.alexei.myonlineshop.servlet.command.util.Validator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static com.erofeev.st.alexei.myonlineshop.servlet.command.util.Validator.*;

public class OrdersCommand implements Command {
    private OrderService orderService = OrderServiceImpl.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String url = ConfigurationManagerImpl.getInstance().getProperty(ConfigurationManagerImpl.ORDERS_PAGE);
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
            List<OrderDTO> orders = orderService.showAllOrders(page, amount);
            request.setAttribute("page", page);
            request.setAttribute("amount", amount);
            request.setAttribute("items", orders);
            request.setAttribute("maxpages", maxPages);
            request.setAttribute("orders", orders);

            String idString = request.getParameter("id");
            if (idString == null) {
                return url;
            }

            Long id = Long.valueOf(idString);
            String statusString = request.getParameter("status");
            StatusEnum status = StatusEnum.valueOf(statusString);
            OrderDTO orderDTO = new OrderDTO();
            orderDTO.setId(id);
            try {
                orderService.changeStatus(orderDTO, status);
            } catch (RepositoryException e) {
                e.printStackTrace();
            } catch (ServiceException e) {
                e.printStackTrace();
            }

        } catch (ServiceException e) {
            e.printStackTrace();
        }
        return url;


    }
}
