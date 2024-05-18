package com.example.internet_store.controllers;

import com.example.internet_store.models.Product;
import com.example.internet_store.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {
    final ProductService productService;


    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
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


}
