package com.gmail.erofeev.st.alexei.myonlineshop.service.impl;

import com.gmail.erofeev.st.alexei.myonlineshop.config.ConnectionService;
import com.gmail.erofeev.st.alexei.myonlineshop.config.connection.ConnectionServiceImpl;
import com.gmail.erofeev.st.alexei.myonlineshop.repository.OrderRepository;
import com.gmail.erofeev.st.alexei.myonlineshop.repository.exception.RepositoryException;
import com.gmail.erofeev.st.alexei.myonlineshop.repository.exception.ServiceException;
import com.gmail.erofeev.st.alexei.myonlineshop.repository.impl.OrderRepositoryImpl;
import com.gmail.erofeev.st.alexei.myonlineshop.repository.model.Item;
import com.gmail.erofeev.st.alexei.myonlineshop.repository.model.Order;
import com.gmail.erofeev.st.alexei.myonlineshop.repository.model.User;
import com.gmail.erofeev.st.alexei.myonlineshop.repository.model.enums.StatusEnum;
import com.gmail.erofeev.st.alexei.myonlineshop.service.OrderService;
import com.gmail.erofeev.st.alexei.myonlineshop.service.converter.ItemConverter;
import com.gmail.erofeev.st.alexei.myonlineshop.service.converter.OrderConverter;
import com.gmail.erofeev.st.alexei.myonlineshop.service.converter.UserConverter;
import com.gmail.erofeev.st.alexei.myonlineshop.service.model.ItemDTO;
import com.gmail.erofeev.st.alexei.myonlineshop.service.model.OrderDTO;
import com.gmail.erofeev.st.alexei.myonlineshop.service.model.UserDTO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;


public class OrderServiceImpl implements OrderService {
    private static volatile OrderService instance = null;
    private ConnectionService connectionService = ConnectionServiceImpl.getInstance();
    private OrderRepository orderRepository = OrderRepositoryImpl.getInstance();

    public static OrderService getInstance() {
        if (instance == null) {
            synchronized (OrderServiceImpl.class) {
                if (instance == null) {
                    instance = new OrderServiceImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public OrderDTO create(UserDTO userDTO, ItemDTO itemDTO, int quantity) throws ServiceException {
        Order createdOrder;
        Date createdDate = new Date(Calendar.getInstance().getTime().getTime());
        Timestamp param = new Timestamp(createdDate.getTime());
        Order order = new Order();
        order.setCreatedDate(param);
        order.setQuantity(quantity);
        Item item = ItemConverter.fromItem(itemDTO);
        User user = UserConverter.fromDTO(userDTO);
        order.setItem(item);
        order.setUser(user);
        order.setStatus(StatusEnum.NEW);
        try (Connection connection = connectionService.getConnection()) {
            try {
                connection.setAutoCommit(false);
                createdOrder = orderRepository.save(connection, order);
                if (createdOrder == null) {
                    connection.rollback();
                    throw new ServiceException("order wasn't created");
                }
                connection.commit();
                return OrderConverter.toDTO(createdOrder);
            } catch (RepositoryException e) {
                connection.rollback();
                throw new ServiceException(e);
            }
        } catch (SQLException e) {
            String message = "Can't open connection: " + e.getMessage();
            throw new ServiceException(message, e);
        }
    }

    @Override
    public List<OrderDTO> getUserOrders(UserDTO userDTO, int pageNumber, int amount) throws ServiceException {
        List<OrderDTO> orderDTOList;
        int offset = (pageNumber - 1) * amount;
        try (Connection connection = connectionService.getConnection()) {
            try {
                connection.setAutoCommit(false);
                User user = UserConverter.fromDTO(userDTO);
                List<Order> orders = orderRepository.findUserOrders(connection, user, offset, amount);
                orderDTOList = OrderConverter.convertList(orders);
                connection.commit();
                return orderDTOList;
            } catch (RepositoryException e) {
                throw new ServiceException(e);
            }
        } catch (SQLException e) {
            String message = "Can't open connection: " + e.getMessage();
            throw new ServiceException(message, e);
        }
    }

    @Override
    public List<OrderDTO> getAllOrders(int pageNumber, int amount) throws ServiceException {
        int offset = (pageNumber - 1) * amount;
        List<OrderDTO> orderDTOList;
        try (Connection connection = connectionService.getConnection()) {
            try {
                connection.setAutoCommit(false);
                List<Order> orders = orderRepository.findAll(connection, offset, amount);
                orderDTOList = OrderConverter.convertList(orders);
                connection.commit();
                return orderDTOList;
            } catch (RepositoryException e) {
                connection.rollback();
                throw new ServiceException(e);
            }
        } catch (SQLException e) {
            String message = "Can't open connection: " + e.getMessage();
            throw new ServiceException(message, e);
        }
    }

    @Override
    public void changeStatus(OrderDTO orderDTO, StatusEnum status) throws ServiceException {
        Order order = OrderConverter.fromDTO(orderDTO);
        order.setStatus(status);
        try (Connection connection = connectionService.getConnection()) {
            try {
                connection.setAutoCommit(false);
                orderRepository.update(connection, order, status);
                connection.commit();
            } catch (RepositoryException e) {
                connection.rollback();
                throw new ServiceException(e);
            }
        } catch (SQLException e) {
            String message = "Can't open connection: " + e.getMessage();
            throw new ServiceException(message, e);
        }
    }

    @Override
    public OrderDTO findById(Long id) throws ServiceException {
        OrderDTO orderDTO;
        try (Connection connection = connectionService.getConnection()) {
            try {
                connection.setAutoCommit(false);
                Order order = orderRepository.findById(connection, id);
                if (order == null) {
                    throw new ServiceException("Order not found");
                }
                orderDTO = OrderConverter.toDTO(order);
                connection.commit();
                return orderDTO;
            } catch (RepositoryException e) {
                throw new ServiceException(e);
            }
        } catch (SQLException e) {
            String message = "Can't open connection: " + e.getMessage();
            throw new ServiceException(message, e);
        }
    }

    @Override
    public Integer getAmountOfOrders() throws ServiceException {
        Integer amount;
        try (Connection connection = connectionService.getConnection()) {
            connection.setAutoCommit(false);
            try {
                amount = orderRepository.getAmount(connection);
                connection.commit();
                return amount;
            } catch (RepositoryException e) {
                throw new ServiceException(e);
            }
        } catch (SQLException e) {
            String message = "Can't open connection: " + e.getMessage();
            throw new ServiceException(message, e);
        }
    }
}

