package com.example.internet_store.controllers;

import com.example.internet_store.dto.ProductDTO;
import com.example.internet_store.models.Group;
import com.example.internet_store.models.Manufacturer;
import com.example.internet_store.models.Product;
import com.example.internet_store.services.GroupService;
import com.example.internet_store.services.ManufacturerService;
import com.example.internet_store.services.ProductService;
import com.example.internet_store.utils.ProductValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {
    final ProductService productService;
    final GroupService groupService;
    final ManufacturerService manufacturerService;
   final ProductValidator productValidator;


    @Autowired
    public ProductController(ProductService productService, GroupService groupService, ManufacturerService manufacturerService,
                             ProductValidator productValidator) {
        this.productService = productService;
        this.groupService = groupService;
        this.manufacturerService = manufacturerService;
      this.productValidator = productValidator;
    }

    @GetMapping("")
    public String index(Model model) {

       List<ProductDTO> productDTOList = productService.getAllProducts().stream().map(product -> productService.convertToProductDTO(product)).toList();
     model.addAttribute("productsModel", productDTOList);

        return "product/productPage";
    }

    @GetMapping("/create")
    public String createProduct(@ModelAttribute ("createProductModel") ProductDTO productDTO, Model model) {
        model.addAttribute("groupListModel", groupService.findAll());
        model.addAttribute("oneGroupModel", new Group());
        model.addAttribute("manufacturerListModel", manufacturerService.getAllManufacturers());
        model.addAttribute("oneManufacturer", new Manufacturer());
        return "/product/createProduct";
    }

    @PostMapping("/create")
    public String postCreateProduct(@ModelAttribute ("createProductModel")
                                      @Valid ProductDTO productDTO, BindingResult bindingResult,
                                    @ModelAttribute ("oneGroupModel") Group group, @ModelAttribute("oneManufacturer") Manufacturer manufacturer, Model model) {

        if (productDTO.getProductURL().isBlank()){
        productDTO.setProductURL(productService.createProductUrl(productDTO.getProductName()));}
        else{
            productDTO.setProductURL(productService.characterReplacementForUrl(productDTO.getProductURL()));
        };
        productValidator.validate(productDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("groupListModel", groupService.findAll());
            model.addAttribute("manufacturerListModel", manufacturerService.getAllManufacturers());
            return "/product/createProduct";
        }

        Product product = productService.convertToProduct(productDTO);
        productService.saveProduct(product, group, manufacturer);
        return "redirect:/product";
    }

    @GetMapping("/{id}")
    public String oneProductPage (@PathVariable("id") int id, Model model) {
        model.addAttribute("oneProductModel", productService.convertToProductDTO(productService.getProductById(id).get()));
        return "/product/oneProductPage";
    }

    @GetMapping("{id}/edit")
    public String getEditPage(Model model, @PathVariable("id") int id) {
        Product product = productService.getProductById(id).get();

        model.addAttribute("oneProductModel", productService.convertToProductDTO(product));
        model.addAttribute("oneGroupModel", product.getGroup());
        model.addAttribute("groupListModel", groupService.findAll());
        model.addAttribute("oneManufacturerModel", product.getManufacturer());
        model.addAttribute("manufacturerListModel", manufacturerService.getAllManufacturers());

        return "/product/editProductPage";
    }


    @PatchMapping("{id}/edit")
    public String editProductPage(@ModelAttribute ("oneProductModel") ProductDTO productDTO, @PathVariable("id") int id,
                                  @ModelAttribute("oneGroupModel") Group group, @ModelAttribute("oneManufacturerModel") Manufacturer manufacturer) {
        Product product = productService.convertToProduct(productDTO);
        productService.editProduct(product, group, manufacturer, id);
        return "redirect:/product";
    }

    }





