package com.example.internet_store.utils;

import com.example.internet_store.dto.ManufacturerDTO;
import com.example.internet_store.services.ManufacturerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
@Component
public class ManufactureValidator implements Validator {

    final ManufacturerService manufacturerService;

    @Autowired
    public ManufactureValidator(ManufacturerService manufacturerService) {
        this.manufacturerService = manufacturerService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(ManufacturerDTO.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
    ManufacturerDTO manufacturerDTO = (ManufacturerDTO) target;
    if(manufacturerService.getManufacturerByName(manufacturerDTO.getManufacturerName()).isPresent()) {
        errors.rejectValue("name", "", "Производитель с таким именем уже существует");
    }


    }
}
