package com.example.internet_store.dto;

import com.example.internet_store.models.Product;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public class ManufacturerDTO
{
    int manufacurerId;
    @NotEmpty(message = "Поле не должно быть пустым")
    String manufacturerName;
    Boolean selceted = false;

    public String getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
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

    public Boolean getSelceted() {
        return selceted;
    }

    public void setSelceted(Boolean selceted) {
        this.selceted = selceted;
    }
}
