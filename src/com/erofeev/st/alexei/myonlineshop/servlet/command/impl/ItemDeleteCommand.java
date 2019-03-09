package com.erofeev.st.alexei.myonlineshop.servlet.command.impl;

import com.erofeev.st.alexei.myonlineshop.config.connection.ConfigurationManagerImpl;
import com.erofeev.st.alexei.myonlineshop.repository.exception.ServiceException;
import com.erofeev.st.alexei.myonlineshop.service.ItemService;
import com.erofeev.st.alexei.myonlineshop.service.impl.ItemServiceImpl;
import com.erofeev.st.alexei.myonlineshop.service.model.ItemDTO;
import com.erofeev.st.alexei.myonlineshop.servlet.command.Command;
import com.erofeev.st.alexei.myonlineshop.servlet.command.util.Validator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ItemDeleteCommand implements Command {
    private ItemService itemService = ItemServiceImpl.getInstance();
    private final static Integer DEFAULT_PAGE_NUMBER = 1;
    private final static Integer DEFAULT_AMOUNT = 10;
    private final static Integer DEFAULT_MAX_AMOUNT = 100;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
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
            Integer amountOfItems = itemService.getAmountOfItems();
            Integer maxPages = Validator.getMaxPage(amountOfItems, amount);
            if ((page <= 1) || (page > maxPages)) {
                page = DEFAULT_PAGE_NUMBER;
            }
            if ((amount <= 1) || (amount > DEFAULT_MAX_AMOUNT)) {
                amount = DEFAULT_AMOUNT;
            }
            List<ItemDTO> items = itemService.findItems(page, amount);
            request.setAttribute("page", page);
            request.setAttribute("amount", amount);
            request.setAttribute("items", items);
            request.setAttribute("maxpages", maxPages);
            String uniqueNumber = request.getParameter("uniquenumber");
            if (uniqueNumber == null) {
                return ConfigurationManagerImpl.getInstance().getProperty(ConfigurationManagerImpl.ITEMS_DELETE_PAGE);
            }
            itemService.delete(uniqueNumber);
            request.setAttribute("info", "item with uniquenumber:" + uniqueNumber + " was deleted");

        } catch (NumberFormatException e) {
            request.setAttribute("error", "page or amount must have digital value");
            return ConfigurationManagerImpl.getInstance().getProperty(ConfigurationManagerImpl.ITEMS_DELETE_PAGE);

        } catch (ServiceException e) {
            request.setAttribute("error", e.getMessage());
            return ConfigurationManagerImpl.getInstance().getProperty(ConfigurationManagerImpl.ITEMS_DELETE_PAGE);
        }
        return ConfigurationManagerImpl.getInstance().getProperty(ConfigurationManagerImpl.ITEMS_DELETE_PAGE);
    }


}