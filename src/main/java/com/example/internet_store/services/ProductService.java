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
import java.util.Optional;

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


    public Optional<Product> getProductById(int id) {
        return productRepositories.findById(id);
    }

    @Transactional
    public void saveProduct(Product product, Group group, Manufacturer manufacturer) {
        System.out.println(manufacturer.getManufacurerId()  + " man id ");
        System.out.println("start save");
        product.setRegistrationDate(new Date());
        System.out.println("test 1");
        product.setDiscount(0);
        System.out.println("test 2");
        product.setPopular(false);
        System.out.println("test 3");
        System.out.println("group id " + group.getGroupId() );
    // product.setProductGroup(groupService.findByGroupName(group.getGroupName()).get());
        product.setProductGroup(groupService.findById(group.getGroupId()).get());

        //product.setManufacturer(manufacturerService.getManufacturerById(6).get());
        product.setManufacturer(manufacturerService.getManufacturerById(manufacturer.getManufacurerId()).get());
//        Manufacturer manufacturer1 = manufacturerService.getManufacturerByName("Xiaomi").get();
//        product.setManufacturer(manufacturer1);


      // product.setProductGroup(groupService.findByGroupName("mobile phones").get());


        productRepositories.save(product);
    }



}
