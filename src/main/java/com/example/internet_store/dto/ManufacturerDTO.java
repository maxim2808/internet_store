package com.example.internet_store.dto;

import com.example.internet_store.models.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

import java.util.Date;
import java.util.List;

public class ManufacturerDTO
{

    @NotEmpty(message = "Поле не должно быть пустым")
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    //    @OneToMany(mappedBy = "manufacturer")
//    List<Product> listProduct;
}
