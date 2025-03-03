package com.example.internet_store.controllers;

import com.example.internet_store.dto.ProductDTO;
import com.example.internet_store.security.PersoneDetail;
import com.example.internet_store.services.GroupService;
import com.example.internet_store.services.PersoneDetailService;
import com.example.internet_store.services.PersoneService;
import com.example.internet_store.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MainController {
    final ProductService productService;
    final PersoneService personeService;
    final GroupService groupService;

    @Value("${pictureFolderInProject}")
    private String pictureFolderInProject;
    @Autowired
    public MainController(ProductService productService, PersoneService personeService, GroupService groupService) {
        this.productService = productService;

        this.personeService = personeService;
        this.groupService = groupService;
    }

    @GetMapping("/main")
    public String mainMethod(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication.getPrincipal() instanceof PersoneDetail){
//            PersoneDetail personeDetail = (PersoneDetail) authentication.getPrincipal();
//            if(personeDetail != null){
//            }
//            if(personeDetail == null){
//            }
            model.addAttribute("authModel", true);
        }
            else {
            model.addAttribute("authModel", false);
        }
        List<ProductDTO> listProductDTO = productService.findPopularProducts(true).stream().map
                (product -> productService.convertToProductDTO(product)).toList();
            productService.addFolderName(listProductDTO);
            model.addAttribute("popularProductsModel", listProductDTO);
            model.addAttribute("isAdminModel", personeService.isAdmin());


        return "mainPage";
    }

    @GetMapping("/admin")
    public String adminMethod(){
        return "adminPage";
    }



    @GetMapping("/test")
    public String testMethod(){
        return "testPage";
    }




}
