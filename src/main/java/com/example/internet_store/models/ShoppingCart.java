package com.example.internet_store.models;


import com.example.internet_store.dto.ProductDTO;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    List<ProductDTO> products = new ArrayList<>();
    int count;

    public List<ProductDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDTO> products) {
        this.products = products;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
