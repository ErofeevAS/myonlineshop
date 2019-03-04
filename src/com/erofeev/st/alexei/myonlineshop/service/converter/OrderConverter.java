package com.erofeev.st.alexei.myonlineshop.service.converter;

import com.erofeev.st.alexei.myonlineshop.repository.ProfileRepository;
import com.erofeev.st.alexei.myonlineshop.repository.model.Item;
import com.erofeev.st.alexei.myonlineshop.repository.model.Order;
import com.erofeev.st.alexei.myonlineshop.repository.model.Profile;
import com.erofeev.st.alexei.myonlineshop.repository.model.User;
import com.erofeev.st.alexei.myonlineshop.repository.model.enums.StatusEnum;
import com.erofeev.st.alexei.myonlineshop.service.ProfileService;
import com.erofeev.st.alexei.myonlineshop.service.impl.ProfileServiceImpl;
import com.erofeev.st.alexei.myonlineshop.service.model.OrderDTO;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class OrderConverter {

    private ProfileService profileService = ProfileServiceImpl.getInstance();

    public static Order fromDTO(OrderDTO orderDTO) {
        Order order = new Order();
        User user = new User();
        user.setId(orderDTO.getUserId());
        order.setId(orderDTO.getId());
        order.setUser(user);
        Item item = new Item();
        orderDTO.setPrice(item.getPrice());
        item.setId(orderDTO.getItemId());
        order.setItem(item);
        Timestamp timestamp = orderDTO.getCreatedDate();
        order.setCreatedDate(timestamp);
        Integer quantity = orderDTO.getQuantity();
        order.setQuantity(quantity);
        return order;
    }

    public static OrderDTO toDTO(Order order) {
        Long orderId = order.getId();
        Long userId = order.getUser().getId();
        String firstName = order.getUser().getFirstName();
        String lastName = order.getUser().getLastName();
        Long itemId = order.getItem().getId();
        String itemName = order.getItem().getName();
        BigDecimal price = order.getItem().getPrice();
        Timestamp createdDate = order.getCreatedDate();
        Integer quantity = order.getQuantity();
        StatusEnum status = order.getStatus();
        OrderDTO orderDTO = new OrderDTO(orderId, userId, firstName, lastName, itemId, itemName, price, createdDate, quantity, status);
        return orderDTO;
    }

    public static List<OrderDTO> convertList(List<Order> orders) {
        List<OrderDTO> orderDTOList = new ArrayList<>();
        for (Order order : orders) {
            orderDTOList.add(toDTO(order));
        }
        return orderDTOList;
    }

}
