package com.example.internet_store.services;

import com.example.internet_store.dto.PersoneDTO;
import com.example.internet_store.dto.PersoneViewDTO;
import com.example.internet_store.models.Persone;
import com.example.internet_store.repositories.PersonRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PersoneService {
    final PersonRepository personRepository;
    final PasswordEncoder passwordEncoder;
    final ModelMapper modelMapper;

    @Autowired
    public PersoneService(PersonRepository personRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.personRepository = personRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    @Transactional()
    public void createPersone(Persone receivedPersone) {
        //  receivedPersone.setPassword( passwordEncoder.encode(receivedPersone.getFakePassword()));
        receivedPersone.setRegistrationDate(new Date());
        receivedPersone.setRole("ROLE_USER");
        personRepository.save(receivedPersone);
    }

    public List<Persone> findAllPersones() {
        return personRepository.findAll();
    }


    public Optional<Persone> findPersoneByEmail(String email) {
        return personRepository.findByEmail(email);
    }

    public Optional<Persone> findPersoneByUsername(String username) {
        return personRepository.findByUsername(username);
    }

    public Optional<Persone> findPersoneByPhoneNumber(String phoneNumber) {
        return personRepository.findByPhoneNumber(phoneNumber);
    }

    public Optional<Persone> findPersoneById(int id) {
        return personRepository.findByPersoneId(id);
    }

    @Transactional
    public void editPersone(Persone updatedPersone, int id) {
        updatedPersone.setPersoneId(id);
        personRepository.save(updatedPersone);
    }

    public Persone convertToPersone(PersoneDTO personeDTO) {
        Persone persone = modelMapper.map(personeDTO, Persone.class);
        persone.setPassword(passwordEncoder.encode(personeDTO.getFakePassword()));
        return persone;
    }

    public PersoneDTO convertToPersoneDTO(Persone persone) {
        PersoneDTO personeDTO = modelMapper.map(persone, PersoneDTO.class);
        return personeDTO;
    }

    public Persone convertToPersoneFromPesonView(PersoneViewDTO personeViewDTO) {
        return modelMapper.map(personeViewDTO, Persone.class);
    }

    public PersoneViewDTO convertToPersoneViewDTO(Persone persone) {
        return modelMapper.map(persone, PersoneViewDTO.class);
    }

    public void mergePersone(Persone persone1, PersoneViewDTO personeViewDTO) {
        persone1.setUsername(personeViewDTO.getUsername());
        persone1.setEmail(personeViewDTO.getEmail());
        persone1.setRole(personeViewDTO.getRole());
        persone1.setPhoneNumber(personeViewDTO.getPhoneNumber());
    }


    public String getCurrentName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return authentication.getName();
        }
        return null;
    }

    public Optional<Persone> getCurrentPersone() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
           return findPersoneByEmail(authentication.getName());
    }


    public boolean isAdmin(){
        if (getCurrentPersone().isPresent()) {
            Persone persone = getCurrentPersone().get();
            String currentRole = persone.getRole();
           return currentRole.equals("ROLE_ADMIN") || currentRole.equals("ROLE_SUPERADMIN");
        }
        return false;
    }
}