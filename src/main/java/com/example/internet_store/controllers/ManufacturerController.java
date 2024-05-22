package com.example.internet_store.controllers;

import com.example.internet_store.models.Manufacturer;
import com.example.internet_store.services.ManufacturerService;
import com.example.internet_store.utils.ManufactureValidator;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/manufacturer")
public class ManufacturerController {
    final ManufacturerService manufacturerService;
    final ManufactureValidator manufactureValidator;

    public ManufacturerController(ManufacturerService manufacturerService, ManufactureValidator manufactureValidator) {
        this.manufacturerService = manufacturerService;
        this.manufactureValidator = manufactureValidator;
    }

    @GetMapping("")
    public String getAllManufacturers(Model model) {
        model.addAttribute("listManufacturersModel", manufacturerService.getAllManufacturers());
        return "/manufacturer/manufacturersPage";
    }

    @GetMapping("/create")
    public String getCreateManufacturer(@ModelAttribute ("createManufacturerModel") Manufacturer manufacturer) {
        return "/manufacturer/createManufacturerPage";
    }

    @PostMapping("/create")
    public String postCreateManufacturer(@ModelAttribute ("createManufacturerModel")  @Valid Manufacturer manufacturer, BindingResult bindingResult){
        manufactureValidator.validate(manufacturer, bindingResult);
        if(bindingResult.hasErrors()){
            return "/manufacturer/createManufacturerPage";
        }
        manufacturerService.saveManufacturer(manufacturer);
        return "redirect:/manufacturer";

    }

}
