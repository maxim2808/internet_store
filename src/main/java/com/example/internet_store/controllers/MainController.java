package com.example.internet_store.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping("/main")
    public String mainMethod(){
        return "mainPage";
    }

    @GetMapping("/admin")
    public String adminMethod(){
        return "adminPage";
    }
}
