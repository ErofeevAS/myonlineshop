package com.erofeev.st.alexei.myonlineshop.servlet.command.impl;

import com.erofeev.st.alexei.myonlineshop.config.connection.ConfigurationManagerImpl;
import com.erofeev.st.alexei.myonlineshop.repository.exception.ServiceException;
import com.erofeev.st.alexei.myonlineshop.service.ItemService;
import com.erofeev.st.alexei.myonlineshop.service.impl.ItemServiceImpl;
import com.erofeev.st.alexei.myonlineshop.service.model.ItemDTO;
import com.erofeev.st.alexei.myonlineshop.servlet.command.Command;
import com.erofeev.st.alexei.myonlineshop.servlet.validator.util.PaginatorUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ItemDeleteCommand implements Command {
    private ItemService itemService = ItemServiceImpl.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        Integer amountOfItems = null;
        try {
            amountOfItems = itemService.getAmountOfItems();
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        Integer amount = PaginatorUtil.getAmount(request);
        request.setAttribute("amountofobject", amountOfItems);
        Integer maxPages = PaginatorUtil.getMaxPage(amountOfItems, amount);
        Integer page = PaginatorUtil.getPage(request, maxPages);
        try {
            String uniqueNumber = request.getParameter("uniquenumber");
            itemService.delete(uniqueNumber);
            List<ItemDTO> items = itemService.findItems(page, amount);
            request.setAttribute("page", page);
            request.setAttribute("amount", amount);
            request.setAttribute("maxpages", maxPages);
            request.setAttribute("items", items);
        } catch (ServiceException e) {
            request.setAttribute("error", e.getMessage());
            return ConfigurationManagerImpl.getInstance().getProperty(ConfigurationManagerImpl.ITEMS_DELETE_PAGE);
        }
        return ConfigurationManagerImpl.getInstance().getProperty(ConfigurationManagerImpl.ITEMS_DELETE_PAGE);
    }


}