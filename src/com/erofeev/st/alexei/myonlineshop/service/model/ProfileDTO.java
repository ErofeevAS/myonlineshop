package com.erofeev.st.alexei.myonlineshop.service.model;

public class ProfileDTO {
    private Long id;
    private String address;
    private String telephone;

    public ProfileDTO() {
    }

    public ProfileDTO(Long id, String address, String telephone) {
        this.id = id;
        this.address = address;
        this.telephone = telephone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Override
    public String toString() {
        return "ProfileDTO{" +
                "id=" + id +
                ", address='" + address + '\'' +
                ", telephone='" + telephone + '\'' +
                '}';
    }
}
