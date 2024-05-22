package com.example.internet_store.services;

import com.example.internet_store.models.Group;
import com.example.internet_store.models.Manufacturer;
import com.example.internet_store.models.Product;
import com.example.internet_store.repositories.ProductRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class ProductService {
    final ProductRepositories productRepositories;
    final GroupService groupService;
    final ManufacturerService manufacturerService;

    @Autowired
    public ProductService(ProductRepositories productRepositories, GroupService groupService, ManufacturerService manufacturerService) {
        this.productRepositories = productRepositories;
        this.groupService = groupService;
        this.manufacturerService = manufacturerService;
    }

    public List<Product> getAllProducts() {
       return productRepositories.findAll();
    }
    @Transactional
    public void saveProduct(Product product, Group group, Manufacturer manufacturer) {

        product.setRegistrationDate(new Date());
        product.setDiscount(0);
        product.setPopular(false);
     product.setProductGroup(groupService.findByGroupName(group.getGroupName()).get());
        product.setManufacturer(manufacturerService.getManufacturerById(manufacturer.getId()).get());
//        Manufacturer manufacturer1 = manufacturerService.getManufacturerByName("Xiaomi").get();
//        product.setManufacturer(manufacturer1);


      // product.setProductGroup(groupService.findByGroupName("mobile phones").get());


        productRepositories.save(product);
    }



}
