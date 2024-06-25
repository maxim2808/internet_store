package com.example.internet_store.repositories;

import com.example.internet_store.models.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ManufacturerRepository extends JpaRepository<Manufacturer, Integer> {

    public Optional<Manufacturer> findByManufacturerName(String manufacturerName);

}
