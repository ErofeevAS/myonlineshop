package com.erofeev.st.alexei.myonlineshop.repository.model;

import com.erofeev.st.alexei.myonlineshop.repository.model.enums.Status;

import java.sql.Date;
import java.sql.Timestamp;

public class Order {
    private User user;
    private Item item;
    private Timestamp createdDate;
    private Integer quantity;
    private Status status;

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

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order{" +
                "user=" + user +
                ", item=" + item +
                ", createdDate=" + createdDate +
                ", quantity=" + quantity +
                ", status=" + status +
                '}';
    }
}
