package com.example.internet_store.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "manufacturer")
public class Manufacturer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "manufacturer_id")
    int manufacurerId;
    @Column(name = "manufacturer_name")
    @NotEmpty(message = "Поле не должно быть пустым")
    String manufacturerName;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "registration_date")
    Date registration_date;
    @OneToMany(mappedBy = "manufacturer")
    List<Product> listProduct;

    public int getManufacurerId() {
        return manufacurerId;
    }

    public void setManufacurerId(int id) {
        this.manufacurerId = id;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(String name) {
        this.manufacturerName = name;
    }

    public Date getRegistration_date() {
        return registration_date;
    }

    public void setRegistration_date(Date registration_date) {
        this.registration_date = registration_date;
    }


    public List<Product> getListProduct() {
        return listProduct;
    }

    public void setListProduct(List<Product> listProduct) {
        this.listProduct = listProduct;
    }
}
