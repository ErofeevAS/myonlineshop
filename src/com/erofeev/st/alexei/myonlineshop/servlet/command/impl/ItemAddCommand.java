package com.erofeev.st.alexei.myonlineshop.servlet.command.impl;

import com.erofeev.st.alexei.myonlineshop.config.connection.ConfigurationManagerImpl;
import com.erofeev.st.alexei.myonlineshop.repository.exception.ServiceException;
import com.erofeev.st.alexei.myonlineshop.service.ItemService;
import com.erofeev.st.alexei.myonlineshop.service.impl.ItemServiceImpl;
import com.erofeev.st.alexei.myonlineshop.service.model.ItemDTO;
import com.erofeev.st.alexei.myonlineshop.servlet.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;

public class ItemAddCommand implements Command {
    private ItemService itemService = ItemServiceImpl.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = ConfigurationManagerImpl.getInstance().getProperty(ConfigurationManagerImpl.ITEM_ADD_PAGE);
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String priceString = request.getParameter("price");

        if ((name == null) || (description == null) || (description == null) || ("".equals(name)) || ("".equals(description)) || ("".equals(priceString))) {
            return page;
        }else {
            BigDecimal price = null;
            try {
                price = BigDecimal.valueOf(Double.parseDouble(priceString));
            } catch (NumberFormatException e) {
                request.setAttribute("error", "Price must be number");
                return page;
            }
            ItemDTO itemDTO = new ItemDTO(name, description, price);
            try {
                itemService.save(itemDTO);
                request.setAttribute("info", " item was saved");
            } catch (ServiceException e) {
                System.out.println(e.getMessage());
                request.setAttribute("error", e.getMessage());
            }
        }
        return page;
    }
}
