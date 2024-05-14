package com.example.internet_store.services;

import com.example.internet_store.models.Persone;
import com.example.internet_store.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PersoneService {
    final PersonRepository personRepository;
    @Autowired
    public PersoneService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Transactional()
    public void createPersone(Persone receivedPersone){
        receivedPersone.setRegistrationDate(new Date());
        receivedPersone.setRole("ROLE_USER");

        personRepository.save(receivedPersone);
    }

    public Optional<Persone> findPersoneByEmail(String email){
        return personRepository.findByEmail(email);
    }


}
