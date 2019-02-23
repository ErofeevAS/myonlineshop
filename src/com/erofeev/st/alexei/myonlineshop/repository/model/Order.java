package com.erofeev.st.alexei.myonlineshop.repository.model;

import java.sql.Date;

public class Order {
    private User user;
    private Item item;
    private Date createdDate;
    private Integer quantity;

    public Order() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Order{" +
                "user=" + user +
                ", item=" + item +
                ", createdDate=" + createdDate +
                ", quantity=" + quantity +
                '}';
    }
}
