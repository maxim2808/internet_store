package com.example.internet_store.controllers;

import com.example.internet_store.models.Group;
import com.example.internet_store.services.GroupService;
import com.example.internet_store.utils.GroupValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/group")
public class GroupController {
final GroupService groupService;
final GroupValidator groupValidator;

@Autowired
public GroupController(GroupService groupService, GroupValidator groupValidator) {
    this.groupService = groupService;
    this.groupValidator = groupValidator;
}

@GetMapping("")
    public String getAllGroup(Model model) {
    model.addAttribute("listGroupModel", groupService.findAll());
    return "/group/groupPage";
}

@GetMapping("/create")
    public String getCreateGroup(@ModelAttribute ("groupModel") Group group) {
    return "/group/createGroupPage";
}

@PostMapping("/create")
    public String createGroup(@ModelAttribute ("groupModel") @Valid Group group, BindingResult bindingResult) {
    groupValidator.validate(group, bindingResult);
    if (bindingResult.hasErrors()) {
        return "/group/createGroupPage";
    }
    groupService.save(group);
    return "redirect:/group";
}


}
