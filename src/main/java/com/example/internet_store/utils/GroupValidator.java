package com.example.internet_store.utils;

import com.example.internet_store.dto.GroupDTO;
import com.example.internet_store.models.Group;
import com.example.internet_store.services.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class GroupValidator implements Validator {

    final GroupService groupService;

    @Autowired
    public GroupValidator(GroupService groupService) {
        this.groupService = groupService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(GroupDTO.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        GroupDTO groupDTO = (GroupDTO) target;
        if(groupService.findByGroupName(groupDTO.getGroupName()).isPresent()) {
            if (groupDTO.getGroupId()!=groupService.findByGroupName(groupDTO.getGroupName()).get().getGroupId()) {
            errors.rejectValue("groupName", "", "Группа товаров с таким названеим уже существует");
            }
        }
        if(groupService.findByURL(groupDTO.getGroupURL()).isPresent()) {
            Group secondGroup = groupService.findByURL(groupDTO.getGroupURL()).get();
            if (groupDTO.getGroupId()!=secondGroup.getGroupId()) {
            errors.rejectValue("groupURL", "", "Группа товаров с таким URL уже существует");
            }
        }


    }
}
