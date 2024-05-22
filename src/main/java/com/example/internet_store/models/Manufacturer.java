package com.example.internet_store.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jdk.jfr.Name;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "manufacturer")
public class Manufacturer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "manufacturer_id")
    int id;
    @Column(name = "manufacturer_name")
    @NotEmpty(message = "Поле не должно быть пустым")
    String name;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "registration_date")
    Date registration_date;
    @OneToMany(mappedBy = "manufacturer")
    List<Product> listProduct;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
