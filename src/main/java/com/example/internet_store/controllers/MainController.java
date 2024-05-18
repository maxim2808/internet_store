package com.example.internet_store.controllers;

import com.example.internet_store.security.PersoneDetail;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping("/main")
    public String mainMethod(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication.getPrincipal() instanceof PersoneDetail){
//            PersoneDetail personeDetail = (PersoneDetail) authentication.getPrincipal();
//            if(personeDetail != null){
//                System.out.println(personeDetail.getPersone().getEmail() + "!!!!!!!!! ");
//            }
//            if(personeDetail == null){
//                System.out.println("null1111111111111");
//            }
            model.addAttribute("authModel", true);

        }
            else {
            model.addAttribute("authModel", false);
            System.out.println("authentication is null222222");
        }

        return "mainPage";
    }

    @GetMapping("/admin")
    public String adminMethod(){
        return "adminPage";
    }
}
