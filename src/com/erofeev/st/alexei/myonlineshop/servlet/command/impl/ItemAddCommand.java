package com.erofeev.st.alexei.myonlineshop.servlet.command.impl;

import com.erofeev.st.alexei.myonlineshop.config.connection.ConfigurationManagerImpl;
import com.erofeev.st.alexei.myonlineshop.repository.exception.ServiceException;
import com.erofeev.st.alexei.myonlineshop.service.ItemService;
import com.erofeev.st.alexei.myonlineshop.service.impl.ItemServiceImpl;
import com.erofeev.st.alexei.myonlineshop.service.model.ItemDTO;
import com.erofeev.st.alexei.myonlineshop.servlet.command.Command;
import com.erofeev.st.alexei.myonlineshop.servlet.validator.Validator;
import com.erofeev.st.alexei.myonlineshop.servlet.validator.impl.ItemValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;

public class ItemAddCommand implements Command {
    private ItemService itemService = ItemServiceImpl.getInstance();
    private Validator validator = ItemValidator.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = ConfigurationManagerImpl.getInstance().getProperty(ConfigurationManagerImpl.ITEM_ADD_PAGE);
        if (!validator.isRequestValid(request)) {
            return page;
        }
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        BigDecimal price =  BigDecimal.valueOf(Double.parseDouble(request.getParameter("price")));
        ItemDTO itemDTO = new ItemDTO(name, description, price);
        try {
            itemService.save(itemDTO);
            request.setAttribute("info", " item was saved");
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
            request.setAttribute("error", e.getMessage());
        }
        return page;
    }
}
