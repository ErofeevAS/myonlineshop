package com.erofeev.st.alexei.myonlineshop.repository.model;

import java.math.BigDecimal;

public class Item {
    private Long id;
    private String name;
    private String description;
    private String uniqueNumber;
    private BigDecimal price;

    public Item(Long id, String name, String description, String uniqueNumber, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.uniqueNumber = uniqueNumber;
        this.price = price;
    }

    public Item() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUniqueNumber() {
        return uniqueNumber;
    }

    public void setUniqueNumber(String uniqueNumber) {
        this.uniqueNumber = uniqueNumber;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", uniqueNumber='" + uniqueNumber + '\'' +
                ", price=" + price +
                '}';
    }
}
