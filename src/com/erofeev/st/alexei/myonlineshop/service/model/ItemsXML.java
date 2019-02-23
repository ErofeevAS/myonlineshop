package com.erofeev.st.alexei.myonlineshop.service.model;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "items", namespace = "http://www.it-academy.by")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"item"})
public class ItemsXML {
    @XmlElement(name = "item", required = true)
    private List<ItemXML> item;

    public ItemsXML() {
    }

    public List<ItemXML> getItem() {
        return item;
    }

    public void setItem(List<ItemXML> item) {
        this.item = item;
    }
}


