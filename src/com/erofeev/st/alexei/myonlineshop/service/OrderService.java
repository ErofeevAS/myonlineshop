package com.erofeev.st.alexei.myonlineshop.service;

import com.erofeev.st.alexei.myonlineshop.repository.exception.RepositoryException;
import com.erofeev.st.alexei.myonlineshop.repository.exception.ServiceException;
import com.erofeev.st.alexei.myonlineshop.repository.model.Item;
import com.erofeev.st.alexei.myonlineshop.repository.model.Order;
import com.erofeev.st.alexei.myonlineshop.repository.model.User;
import com.erofeev.st.alexei.myonlineshop.repository.model.enums.StatusEnum;
import com.erofeev.st.alexei.myonlineshop.service.model.ItemDTO;
import com.erofeev.st.alexei.myonlineshop.service.model.OrderDTO;
import com.erofeev.st.alexei.myonlineshop.service.model.UserDTO;

import java.util.List;

public interface OrderService {

    OrderDTO create(UserDTO userDTO, ItemDTO itemDTO, int quantity) throws ServiceException;

    List<OrderDTO> getUserOrders(UserDTO userDTO, int pageNumber, int amount) throws ServiceException;

    List<OrderDTO> showAllOrders(int pageNumber, int amount);

    void changeStatus(OrderDTO orderDTO, StatusEnum status) throws RepositoryException, ServiceException;

    OrderDTO findById(Long id) throws ServiceException;

    Integer getAmountOfOrders() throws ServiceException;
}
