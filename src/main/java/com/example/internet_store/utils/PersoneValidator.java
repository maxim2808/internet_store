package com.example.internet_store.utils;

import com.example.internet_store.models.Persone;
import com.example.internet_store.services.PersoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersoneValidator implements Validator {
    final PersoneService personeService;

    @Autowired
    public PersoneValidator(PersoneService personeService) {
        this.personeService = personeService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(Persone.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
    Persone persone = (Persone) target;
      //  System.out.println(persone.getFakePassword() + " and repeat " + persone.getRepeatPassword());
    if(personeService.findPersoneByEmail(persone.getEmail()).isPresent()){
        errors.rejectValue("email", "", "Пользователь с таким email уже существует");
    }

    if(personeService.findPersoneByUsername(persone.getUsername()).isPresent()){
        errors.rejectValue("username", "", "Пользователь с именем " + persone.getUsername() + "Уже существует");
    }

    if (personeService.findPersoneByPhoneNumber(persone.getPhoneNumber()).isPresent()){
        errors.rejectValue("phoneNumber", "", "Телефон " + persone.getPhoneNumber() + " уже используется");
    }

    if(!persone.getFakePassword().equals(persone.getRepeatPassword())){
        errors.rejectValue("fakePassword", "", "пароли не совпадают");
    }
    }
}
