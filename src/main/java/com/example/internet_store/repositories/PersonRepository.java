package com.example.internet_store.repositories;

import com.example.internet_store.models.Persone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Persone, Integer> {
    public Optional<Persone> findByEmail(String email);
    public Optional<Persone> findByUsername(String username);
    public Optional<Persone> findByPhoneNumber(String phoneNumber);
    public Optional<Persone> findByPersoneId(int personId);
}
