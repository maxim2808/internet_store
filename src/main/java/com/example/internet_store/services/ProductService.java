package com.example.internet_store.services;

import com.example.internet_store.models.Product;
import com.example.internet_store.repositories.ProductRepositories;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    final ProductRepositories productRepositories;


    public ProductService(ProductRepositories productRepositories) {
        this.productRepositories = productRepositories;
    }

    public List<Product> getAllProducts() {
       return productRepositories.findAll();
    }



}
