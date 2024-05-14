package com.example.internet_store.controllers;

import com.example.internet_store.models.Persone;
import com.example.internet_store.services.PersoneService;
import com.example.internet_store.utils.PersoneValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthenticationController {

    final PersoneService personeService;
    final PersoneValidator personeValidator;
    @Autowired
    public AuthenticationController(PersoneService personeService, PersoneValidator personeValidator) {
        this.personeService = personeService;
        this.personeValidator = personeValidator;
    }

    @GetMapping("/login")
    public String getLogin(){
        return "/auth/loginPage";
    }



    @GetMapping("/registration")
    public String getRegistration(@ModelAttribute ("modelPersone")  Persone persone) {
        return "/auth/registrationPage";
    }

    @PostMapping("/registration")
    public String postRegistration(@ModelAttribute ("modelPersone") @Valid Persone persone, BindingResult bindingResult) {

        personeValidator.validate(persone, bindingResult);
        if (bindingResult.hasErrors()) {
            return "/auth/registrationPage";
        }
        personeService.createPersone(persone);
        return "redirect:/auth/login";

    }



}
