package com.example.internet_store.services;

import com.example.internet_store.models.Manufacturer;
import com.example.internet_store.repositories.ManufacturerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ManufacturerService  {
    final ManufacturerRepository manufacturerRepository;

    @Autowired
    public ManufacturerService(ManufacturerRepository manufacturerRepository) {
        this.manufacturerRepository = manufacturerRepository;
    }

    public List<Manufacturer> getAllManufacturers() {
        return manufacturerRepository.findAll();
    }

    public Optional<Manufacturer> getManufacturerByName(String name) {
       return manufacturerRepository.findByName(name);
    }

    public Optional<Manufacturer> getManufacturerById(int id) {
        return manufacturerRepository.findById(id);
    }

    @Transactional
    public void saveManufacturer(Manufacturer manufacturer) {
        manufacturer.setRegistration_date(new Date());
        manufacturerRepository.save(manufacturer);
    }



}
