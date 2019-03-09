package com.erofeev.st.alexei.myonlineshop.service.impl;

import com.erofeev.st.alexei.myonlineshop.config.ConnectionService;
import com.erofeev.st.alexei.myonlineshop.repository.OrderRepository;
import com.erofeev.st.alexei.myonlineshop.config.connection.ConnectionServiceImpl;
import com.erofeev.st.alexei.myonlineshop.repository.exception.RepositoryException;
import com.erofeev.st.alexei.myonlineshop.repository.exception.ServiceException;
import com.erofeev.st.alexei.myonlineshop.repository.impl.OrderRepositoryImpl;
import com.erofeev.st.alexei.myonlineshop.repository.model.Item;
import com.erofeev.st.alexei.myonlineshop.repository.model.Order;
import com.erofeev.st.alexei.myonlineshop.repository.model.User;
import com.erofeev.st.alexei.myonlineshop.repository.model.enums.StatusEnum;
import com.erofeev.st.alexei.myonlineshop.service.OrderService;
import com.erofeev.st.alexei.myonlineshop.service.converter.ItemConverter;
import com.erofeev.st.alexei.myonlineshop.service.converter.OrderConverter;
import com.erofeev.st.alexei.myonlineshop.service.converter.UserConverter;
import com.erofeev.st.alexei.myonlineshop.service.model.ItemDTO;
import com.erofeev.st.alexei.myonlineshop.service.model.OrderDTO;
import com.erofeev.st.alexei.myonlineshop.service.model.UserDTO;
import com.mysql.cj.x.protobuf.MysqlxCrud;

import java.sql.Connection;
import java.sql.Date;
import java.sql.Timestamp;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class OrderServiceImpl implements OrderService {
    private static volatile OrderService instance = null;
    private ConnectionService connectionService = ConnectionServiceImpl.getInstance();
    private OrderRepository orderRepository = OrderRepositoryImpl.getInstance();

    public static OrderService getInstance() {
        if (instance == null) {
            synchronized (OrderService.class) {
                if (instance == null) {
                    instance = new OrderServiceImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public OrderDTO create(UserDTO userDTO, ItemDTO itemDTO, int quantity) throws ServiceException {
        Order createdOrder = null;
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
            connection.setAutoCommit(false);
            createdOrder = orderRepository.save(connection, order);
            if (createdOrder == null) {
                throw new ServiceException("order wasn't created");
            }
            connection.commit();
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        } catch (SQLException e) {
            String message = "order service create exception: " + e.getMessage();
            throw new ServiceException(message, e);

        }
        return OrderConverter.toDTO(createdOrder);
    }

    @Override
    public List<OrderDTO> getUserOrders(UserDTO userDTO, int pageNumber, int amount) throws ServiceException {
        List<OrderDTO> orderDTOList;
        int offset = (pageNumber - 1) * amount;
        try (Connection connection = connectionService.getConnection()) {
            connection.setAutoCommit(false);
            User user = UserConverter.fromDTO(userDTO);
            List<Order> orders = orderRepository.findUserOrders(connection, user, offset, amount);
            orderDTOList = OrderConverter.convertList(orders);
            connection.commit();
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        } catch (SQLException e) {
            String message = " " + e.getMessage();
            throw new ServiceException(message, e);
        }
        return orderDTOList;
    }

    @Override
    public List<OrderDTO> showAllOrders(int pageNumber, int amount) {
        int offset = (pageNumber - 1) * amount;
        List<OrderDTO> orderDTOList = new ArrayList<>();
        try (Connection connection = connectionService.getConnection()) {
            connection.setAutoCommit(false);
            List<Order> orders = orderRepository.findAll(connection, offset, amount);
            orderDTOList = OrderConverter.convertList(orders);
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderDTOList;
    }


    @Override
    public void changeStatus(OrderDTO orderDTO, StatusEnum status) throws RepositoryException, ServiceException {
        Order order = OrderConverter.fromDTO(orderDTO);
        order.setStatus(status);
        try (Connection connection = connectionService.getConnection()) {
            connection.setAutoCommit(false);
            orderRepository.update(connection, order, status);
            connection.commit();
        } catch (SQLException e) {
            String message = "Can't establish connection to database";
            throw new ServiceException(message, e);
        }
    }

    @Override
    public OrderDTO findById(Long id) throws ServiceException {
        OrderDTO orderDTO = null;
        try (Connection connection = connectionService.getConnection()) {
            connection.setAutoCommit(false);
            Order order = orderRepository.findById(connection, id);
            if (order == null) {
                throw new ServiceException("Order not found");
            }
            orderDTO = OrderConverter.toDTO(order);
            connection.commit();
            return orderDTO;
        } catch (SQLException e) {
            throw new ServiceException("Problem with database connection");
        }
    }

    @Override
    public Integer getAmountOfOrders() throws ServiceException {
        Integer amount = null;
        try (Connection connection = connectionService.getConnection()) {
            connection.setAutoCommit(false);
            try {
                amount = orderRepository.getAmount(connection);
            } catch (RepositoryException e) {
                throw new ServiceException(e);
            }
            connection.commit();
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
        return amount;
    }
}

