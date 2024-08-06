package com.example.internet_store.controllers;

import com.example.internet_store.dto.GroupDTO;
import com.example.internet_store.models.Group;
import com.example.internet_store.models.Manufacturer;
import com.example.internet_store.services.GroupService;
import com.example.internet_store.services.ManufacturerService;
import com.example.internet_store.services.ProductService;
import com.example.internet_store.utils.GroupValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/group")
public class GroupController {
final GroupService groupService;
final GroupValidator groupValidator;
final ManufacturerService manufacturerService;
final ProductService productService;

@Autowired
public GroupController(GroupService groupService, GroupValidator groupValidator, ManufacturerService manufacturerService, ProductService productService) {
    this.groupService = groupService;
    this.groupValidator = groupValidator;
    this.manufacturerService = manufacturerService;
    this.productService = productService;
}

@GetMapping("")
    public String getAllGroup(Model model) {
    model.addAttribute("listGroupModel", groupService.findAll());
    return "/group/groupPage";
}

@GetMapping("/create")
    public String getCreateGroup(@ModelAttribute ("groupModel") GroupDTO groupDTO) {
    return "/group/createGroupPage";
}

@PostMapping("/create")
    public String createGroup(@ModelAttribute ("groupModel") @Valid GroupDTO groupDTO, BindingResult bindingResult) {

    if (groupDTO.getGroupURL().isBlank()){
        groupDTO.setGroupURL(productService.createUrl(groupDTO.getGroupName()));}
    else{
        groupDTO.setGroupURL(productService.characterReplacementForUrl(groupDTO.getGroupURL()));
    };


    groupValidator.validate(groupDTO, bindingResult);
    if (bindingResult.hasErrors()) {
        return "/group/createGroupPage";
    }
    Group group = groupService.convertToGroup(groupDTO);
    groupService.save(group);
    return "redirect:/group";
}

@GetMapping("/view/{id}")
    public String getViewGroup(@PathVariable("id") int id, Model model) {
    model.addAttribute("groupModel", groupService.convertToDTO(groupService.findById(id).get()));
    List<Manufacturer>  manufacturerList = manufacturerService.getAllManufacturersByGroup(id);

    return "/group/viewGroupPage";
}

@GetMapping("/edit/{id}")
    public String getViewGroupEdit(@PathVariable("id") int id, Model model) {
    model.addAttribute("groupModel", groupService.convertToDTO(groupService.findById(id).get()));
    return "/group/editGroupPage";
}
@GetMapping("/delete/error")
public String deleteGroupError() {
    return "/group/errorDeleteGroupPage";
}

@PatchMapping("/edit/{id}")
    public String patchViewGroupEdit(@PathVariable("id") int id, @ModelAttribute("groupModel") @Valid GroupDTO groupDTO,
                                     BindingResult bindingResult) {
    groupDTO.setGroupId(id);
    groupValidator.validate(groupDTO, bindingResult);
    if (bindingResult.hasErrors()) {
        return "/group/editGroupPage";
    }
    groupService.edit(groupService.convertToGroup(groupDTO), id);
    return "redirect:/group";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteGroup(@PathVariable("id") int id) {
    if(groupService.findById(id).get().getProductList().isEmpty()||groupService.findById(id).get().getProductList()==null) {
        groupService.deleteById(id);
        return "redirect:/group";
    }
    else {
        return "redirect:/group/delete/error";
    }

    }

}
