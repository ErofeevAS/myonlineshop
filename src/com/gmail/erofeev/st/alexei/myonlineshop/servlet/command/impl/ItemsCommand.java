package com.gmail.erofeev.st.alexei.myonlineshop.servlet.command.impl;

import com.gmail.erofeev.st.alexei.myonlineshop.config.connection.ConfigurationManagerImpl;
import com.gmail.erofeev.st.alexei.myonlineshop.repository.exception.ServiceException;
import com.gmail.erofeev.st.alexei.myonlineshop.service.ItemService;
import com.gmail.erofeev.st.alexei.myonlineshop.service.impl.ItemServiceImpl;
import com.gmail.erofeev.st.alexei.myonlineshop.service.model.ItemDTO;
import com.gmail.erofeev.st.alexei.myonlineshop.servlet.command.Command;
import com.gmail.erofeev.st.alexei.myonlineshop.servlet.validator.util.PaginatorUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ItemsCommand implements Command {
    private ItemService itemService = ItemServiceImpl.getInstance();
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        Integer amountOfItems = null;
        try {
            amountOfItems = itemService.getAmountOfItems();
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        request.setAttribute("amountofobject", amountOfItems);
        Integer amount = PaginatorUtil.getAmount(request);
        Integer maxPages = PaginatorUtil.getMaxPage(amountOfItems, amount);
        Integer page = PaginatorUtil.getPage(request, maxPages);
        try {
            List<ItemDTO> items = itemService.findItems(page, amount);
            request.setAttribute("page", page);
            request.setAttribute("amount", amount);
            request.setAttribute("maxpages", maxPages);
            request.setAttribute("items", items);
        } catch (ServiceException e) {
            request.setAttribute("error", e.getMessage());
            return ConfigurationManagerImpl.getInstance().getProperty(ConfigurationManagerImpl.ITEMS_PAGE);
        }
        return ConfigurationManagerImpl.getInstance().getProperty(ConfigurationManagerImpl.ITEMS_PAGE);
    }


}
