package com.example.internet_store.services;

import com.example.internet_store.models.Persone;
import com.example.internet_store.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PersoneService {
    final PersonRepository personRepository;
    final PasswordEncoder passwordEncoder;
    @Autowired
    public PersoneService(PersonRepository personRepository, PasswordEncoder passwordEncoder) {
        this.personRepository = personRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional()
    public void createPersone(Persone receivedPersone){
        receivedPersone.setPassword( passwordEncoder.encode(receivedPersone.getFakePassword()));

        receivedPersone.setRegistrationDate(new Date());
        receivedPersone.setRole("ROLE_USER");
        personRepository.save(receivedPersone);
    }

    public Optional<Persone> findPersoneByEmail(String email){
        return personRepository.findByEmail(email);
    }

    public Optional<Persone> findPersoneByUsername(String username){
        return personRepository.findByUsername(username);
    }

    public  Optional<Persone> findPersoneByPhoneNumber(String phoneNumber){
        return personRepository.findByPhoneNumber(phoneNumber);
    }


}
