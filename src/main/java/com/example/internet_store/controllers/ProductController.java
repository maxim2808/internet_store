package com.example.internet_store.controllers;

import com.example.internet_store.models.Group;
import com.example.internet_store.models.Manufacturer;
import com.example.internet_store.models.Product;
import com.example.internet_store.services.GroupService;
import com.example.internet_store.services.ManufacturerService;
import com.example.internet_store.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {
    final ProductService productService;
    final GroupService groupService;
    final ManufacturerService manufacturerService;


    @Autowired
    public ProductController(ProductService productService, GroupService groupService, ManufacturerService manufacturerService) {
        this.productService = productService;
        this.groupService = groupService;
        this.manufacturerService = manufacturerService;
    }

    @GetMapping("")
    public String index(Model model) {
//        List<Product> listProduct = productService.getAllProducts();
//        for (Product product : listProduct) {
//            System.out.println(product.getFinalPrice());
//        }
     model.addAttribute("productsModel", productService.getAllProducts());

        return "product/productPage";
    }

    @GetMapping("/create")
    public String createProduct(@ModelAttribute ("createProductModel") Product product, Model model) {
        model.addAttribute("groupListModel", groupService.findAll());
        model.addAttribute("oneGroupModel", new Group());
        model.addAttribute("manufacturerListModel", manufacturerService.getAllManufacturers());
        model.addAttribute("oneManufacturer", new Manufacturer());
        return "/product/createProduct";
    }

    @PostMapping("/create")
    public String postCreateProduct(@ModelAttribute ("createProductModel") Product product, @ModelAttribute ("oneGroupModel") Group group,
                                    @ModelAttribute("oneManufacturer") Manufacturer manufacturer) {
        System.out.println("!!!!!" + manufacturer.getName());
        productService.saveProduct(product, group, manufacturer);
        return "redirect:/product";
    }


}
