package com.example.internet_store.controllers;

import com.example.internet_store.security.PersoneDetail;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {

    @GetMapping("/user/profile")
    public String getProfile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      PersoneDetail personeDetail = (PersoneDetail)authentication.getPrincipal();
      model.addAttribute("personeModel", personeDetail.getPersone());
      return "/user/userPage";

    }
}
