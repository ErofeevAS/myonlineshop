package com.gmail.erofeev.st.alexei.myonlineshop.service;

import com.gmail.erofeev.st.alexei.myonlineshop.repository.exception.ServiceException;
import com.gmail.erofeev.st.alexei.myonlineshop.repository.model.enums.StatusEnum;
import com.gmail.erofeev.st.alexei.myonlineshop.service.model.ItemDTO;
import com.gmail.erofeev.st.alexei.myonlineshop.service.model.OrderDTO;
import com.gmail.erofeev.st.alexei.myonlineshop.service.model.UserDTO;

import java.util.List;

public interface OrderService {

    OrderDTO create(UserDTO userDTO, ItemDTO itemDTO, int quantity) throws ServiceException;

    List<OrderDTO> getUserOrders(UserDTO userDTO, int pageNumber, int amount) throws ServiceException;

    List<OrderDTO> getAllOrders(int pageNumber, int amount) throws ServiceException;

    void changeStatus(OrderDTO orderDTO, StatusEnum status) throws ServiceException;

    OrderDTO findById(Long id) throws ServiceException;

    Integer getAmountOfOrders() throws ServiceException;
}