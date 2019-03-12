package com.gmail.erofeev.st.alexei.myonlineshop.service.model;

import com.gmail.erofeev.st.alexei.myonlineshop.repository.model.enums.StatusEnum;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class OrderDTO {
    private Long id;
    private Long userId;
    private String firstName;
    private String lastName;
    private Long itemId;
    private String itemName;
    private BigDecimal price;
    private Timestamp createdDate;
    private Integer quantity;
    private StatusEnum status;

    public OrderDTO() {
    }

    public OrderDTO(Long id, Long userId, String firstName, String lastName, Long itemId, String itemName, BigDecimal price, Timestamp createdDate, Integer quantity, StatusEnum status) {
        this.id = id;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.itemId = itemId;
        this.itemName = itemName;
        this.price = price;
        this.createdDate = createdDate;
        this.quantity = quantity;
        this.status = status;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
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
        return "OrderDTO{" +
                "id=" + id +
                ", userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", itemId=" + itemId +
                ", itemName='" + itemName + '\'' +
                ", price=" + price +
                ", createdDate=" + createdDate +
                ", quantity=" + quantity +
                ", status=" + status +
                '}';
    }
}
