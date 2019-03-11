package com.erofeev.st.alexei.myonlineshop.servlet.command.impl;

import com.erofeev.st.alexei.myonlineshop.config.connection.ConfigurationManagerImpl;
import com.erofeev.st.alexei.myonlineshop.repository.exception.ServiceException;
import com.erofeev.st.alexei.myonlineshop.repository.model.enums.StatusEnum;
import com.erofeev.st.alexei.myonlineshop.service.OrderService;
import com.erofeev.st.alexei.myonlineshop.service.impl.OrderServiceImpl;
import com.erofeev.st.alexei.myonlineshop.service.model.OrderDTO;
import com.erofeev.st.alexei.myonlineshop.servlet.command.Command;
import com.erofeev.st.alexei.myonlineshop.servlet.validator.util.PaginatorUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;


public class OrdersCommand implements Command {
    private OrderService orderService = OrderServiceImpl.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String url = ConfigurationManagerImpl.getInstance().getProperty(ConfigurationManagerImpl.ORDERS_PAGE);
        Integer amountOfItems;
        try {
            amountOfItems = orderService.getAmountOfOrders();
        } catch (ServiceException e) {
            e.printStackTrace();
            return url;
        }
        request.setAttribute("amountofobject", amountOfItems);

        List<String> statusList = new ArrayList<>();
        for (StatusEnum status : StatusEnum.values()) {
            statusList.add(status.name());
        }
        request.setAttribute("status_list", statusList);
        Integer amount = PaginatorUtil.getAmount(request);
        Integer maxPages = PaginatorUtil.getMaxPage(amountOfItems, amount);
        Integer page = PaginatorUtil.getPage(request, maxPages);
        List<OrderDTO> orders = orderService.getAllOrders(page, amount);
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
        }  catch (ServiceException e) {
            e.printStackTrace();
        }
        return url;
    }


}
