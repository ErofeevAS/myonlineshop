package com.gmail.erofeev.st.alexei.myonlineshop.repository.model;

import com.gmail.erofeev.st.alexei.myonlineshop.repository.model.enums.Permissions;

public class Permission {
    private Long id;
    private Permissions name;

    public Permission() {
    }

    public Permission(Long id, Permissions name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Permissions getName() {
        return name;
    }

    public void setName(Permissions name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Permission{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
