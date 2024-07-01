package com.example.internet_store.controllers;

import com.example.internet_store.dto.PersoneDTO;
import com.example.internet_store.dto.PersoneViewDTO;
import com.example.internet_store.models.Persone;
import com.example.internet_store.security.PersoneDetail;
import com.example.internet_store.services.PersoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {
    final PersoneService personeService;
    @Autowired
    public UserController(PersoneService personeService) {
        this.personeService = personeService;
    }

    @GetMapping("user/allUser")
    public String allUser(Model model) {
        List<PersoneViewDTO> listPersoneViewDTO = personeService.findAllPersones().stream().map(persone -> personeService.convertToPersoneViewDTO(persone)).toList();
        model.addAttribute("listPersoneDTOModel", listPersoneViewDTO);
     //   System.out.println("cuurent name " + personeService.getCurrentRole());




        return "user/allUserPage";
    }

    @GetMapping("/user/profile")
    public String getProfile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      PersoneDetail personeDetail = (PersoneDetail)authentication.getPrincipal();
      model.addAttribute("personeModel", personeDetail.getPersone());

      return "/user/userPage";

    }

    @GetMapping("user/view/{id}")
    public String getView(@PathVariable ("id" )int id, Model model) {
        model.addAttribute("userModel", personeService.convertToPersoneViewDTO(personeService.findPersoneById(id).get()));
        Persone persone = personeService.findPersoneByEmail(personeService.getCurrentName()).get();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentLogin = authentication.getName();
        Persone currentPersone = personeService.findPersoneByEmail(currentLogin).get();
        boolean isAdmin = persone.getRole().equals("ROLE_SUPERADMIN");
        if(currentPersone.getPersoneId()==id){
            isAdmin=false;
        }
        if (isAdmin==true){
            List<String> listRole = new ArrayList<>();
            listRole.add("ROLE_USER");
            listRole.add("ROLE_ADMIN");
            listRole.add("ROLE_SUPERADMIN");
            model.addAttribute("listRoleModel", listRole);

        }
        model.addAttribute("isAdmin", isAdmin);
        return "/user/userViewPage";

    }

//    @GetMapping("user/view/edit/{id}")
//    public String getEdit(@PathVariable ("id" )int id, Model model) {
//        model.addAttribute("userModel", personeService.findPersoneById(id).get());
//        return "/user/userViewPage";
//
//    }

    @PatchMapping("user/edit/{id}")
    public String patchEditUser(@PathVariable ("id" )int id, @ModelAttribute PersoneViewDTO personeViewDTO) {
       // System.out.println("viewDTO role: " + personeViewDTO.getRole());
        Persone persone = personeService.findPersoneById(id).get();
        personeService.mergePersone(persone, personeViewDTO);
        personeService.editPersone(persone, id);
        return "redirect:/user/allUser";



    }






    @GetMapping("/superadmin")
    public String getSuperAdmin(Model model) {
        return "/user/superAdminPage";
    }

    @PatchMapping("/superadmin")
    public String patchSuperAdmin(Model model) {
        return "/user/superAdminPage";
    }



}
