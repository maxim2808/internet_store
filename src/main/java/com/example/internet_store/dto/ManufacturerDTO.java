package com.example.internet_store.dto;

import com.example.internet_store.models.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

import java.util.Date;
import java.util.List;

public class ManufacturerDTO
{
    int manufacurerId;
    @NotEmpty(message = "Поле не должно быть пустым")
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
        List<Product> listProduct;

    public int getManufacurerId() {
        return manufacurerId;
    }

    public void setManufacurerId(int manufacurerId) {
        this.manufacurerId = manufacurerId;
    }

    public List<Product> getListProduct() {
        return listProduct;
    }

    public void setListProduct(List<Product> listProduct) {
        this.listProduct = listProduct;
    }
}
