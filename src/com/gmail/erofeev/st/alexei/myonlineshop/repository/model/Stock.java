package com.gmail.erofeev.st.alexei.myonlineshop.repository.model;

public class Stock {
    private Item item;
    private Integer quantity;

    public Stock() {
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "item=" + item +
                ", quantity=" + quantity +
                '}';
    }
}
