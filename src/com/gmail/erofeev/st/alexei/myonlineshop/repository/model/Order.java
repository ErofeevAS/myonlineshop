package com.gmail.erofeev.st.alexei.myonlineshop.repository.model;

import com.gmail.erofeev.st.alexei.myonlineshop.repository.model.enums.StatusEnum;

import java.sql.Timestamp;

public class Order {
    private Long id;
    private User user;
    private Item item;
    private Timestamp createdDate;
    private Integer quantity;
    private StatusEnum status;

    public Order() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
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
