package com.example.internet_store.repositories;

import com.example.internet_store.models.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductGroupRepository extends JpaRepository<Group, Integer> {
    public Optional<Group> findByGroupName(String groupName);
}
