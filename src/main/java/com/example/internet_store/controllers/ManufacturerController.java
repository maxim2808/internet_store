package com.example.internet_store.controllers;

import com.example.internet_store.dto.ManufacturerDTO;
import com.example.internet_store.models.Manufacturer;
import com.example.internet_store.services.ManufacturerService;
import com.example.internet_store.utils.ManufactureValidator;
import jakarta.validation.Valid;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
    public String getCreateManufacturer(@ModelAttribute ("createManufacturerModel") ManufacturerDTO manufacturerDTO) {
        return "/manufacturer/createManufacturerPage";
    }

    @PostMapping("/create")
    public String postCreateManufacturer(@ModelAttribute ("createManufacturerModel")  @Valid ManufacturerDTO manufacturerDTO, BindingResult bindingResult){
        manufactureValidator.validate(manufacturerDTO, bindingResult);
        if(bindingResult.hasErrors()){
            return "/manufacturer/createManufacturerPage";
        }
        Manufacturer manufacturer = manufacturerService.convertToManufacturer(manufacturerDTO);
        manufacturerService.saveManufacturer(manufacturer);
        return "redirect:/manufacturer";
    }

    @GetMapping("/view/{id}")
    public String getManufacturerView(@PathVariable int id, Model model) {
        model.addAttribute("manufacturerModel", manufacturerService.convertToManufacturerDTO(manufacturerService.findById(id).get()));
        return "/manufacturer/viewManufacturerPage";
    }

    @GetMapping("/view/edit/{id}")
    public String getManufacturerViewEdit(@PathVariable int id, Model model) {
        model.addAttribute("manufacturerModel", manufacturerService.convertToManufacturerDTO(manufacturerService.findById(id).get()));
        return "/manufacturer/editManufacturerPage";

    }

    @PatchMapping("/view/edit/{id}")
    public String getManufacturerViewEdit(@PathVariable int id, @ModelAttribute("manufacturerModel") ManufacturerDTO manufacturerDTO) {
        manufacturerService.editManufacturer(manufacturerService.convertToManufacturer(manufacturerDTO), id);
        return "redirect:/manufacturer";}


    @GetMapping("/delete/error")
    public String getManufacturerDeleteError() {
        return "/manufacturer/errorDeleteManufacturerPage";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteManufacturer(@PathVariable int id) {
        Manufacturer manufacturer = manufacturerService.findById(id).get();
        if (manufacturer.getListProduct().isEmpty()||manufacturer.getListProduct()==null) {
            manufacturerService.deleteManufacturer(id);
            return "redirect:/manufacturer";
        }
        else return "redirect:/manufacturer/delete/error";
    }





}
