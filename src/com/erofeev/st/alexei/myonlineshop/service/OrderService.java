package com.erofeev.st.alexei.myonlineshop.service;

import com.erofeev.st.alexei.myonlineshop.repository.model.Item;
import com.erofeev.st.alexei.myonlineshop.repository.model.Order;
import com.erofeev.st.alexei.myonlineshop.repository.model.User;
import com.erofeev.st.alexei.myonlineshop.repository.model.enums.Status;

public interface OrderService {

    Order create(User user, Item item, int quantity);

    Order show(User user);

    Boolean changeStatus(Order order, Status status);
}
