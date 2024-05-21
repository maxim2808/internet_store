package com.example.internet_store.services;

import com.example.internet_store.models.Product;
import com.example.internet_store.repositories.ProductRepositories;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class ProductService {
    final ProductRepositories productRepositories;
    final GroupService groupService;


    public ProductService(ProductRepositories productRepositories, GroupService groupService) {
        this.productRepositories = productRepositories;
        this.groupService = groupService;
    }

    public List<Product> getAllProducts() {
       return productRepositories.findAll();
    }
    @Transactional
    public void saveProduct(Product product) {
        product.setRegistrationDate(new Date());
        product.setDiscount(0);
        product.setPopular(false);
        product.setProductGroup(groupService.findByGroupName("mobile phones").get());
        productRepositories.save(product);
    }



}
