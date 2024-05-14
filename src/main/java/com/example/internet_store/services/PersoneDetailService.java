package com.example.internet_store.services;

import com.example.internet_store.models.Persone;
import com.example.internet_store.repositories.PersonRepository;
import com.example.internet_store.security.PersoneDetail;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersoneDetailService implements UserDetailsService {
   final PersonRepository personRepository;

    public PersoneDetailService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Persone> persone = personRepository.findByEmail(username);
        if(persone.isEmpty()){throw new UsernameNotFoundException("User with username " + username + " not found");}
        return new PersoneDetail(persone.get());
    }
}
