package com.example.internet_store.services;

import com.example.internet_store.dto.ManufacturerDTO;
import com.example.internet_store.models.Manufacturer;
import com.example.internet_store.repositories.ManufacturerRepository;
import org.modelmapper.ModelMapper;
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
    final ModelMapper modelMapper;

    @Autowired
    public ManufacturerService(ManufacturerRepository manufacturerRepository, ModelMapper modelMapper) {
        this.manufacturerRepository = manufacturerRepository;
        this.modelMapper = modelMapper;
    }

    public List<Manufacturer> getAllManufacturers() {
        return manufacturerRepository.findAll();
    }

    public Optional<Manufacturer> getManufacturerByName(String name) {
       return manufacturerRepository.findByManufacturerName(name);
    }

    public Optional<Manufacturer> findById(int id) {
        return manufacturerRepository.findById(id);
    }

    @Transactional
    public void saveManufacturer(Manufacturer manufacturer) {
        manufacturer.setRegistration_date(new Date());
        manufacturerRepository.save(manufacturer);
    }


    public Manufacturer convertToManufacturer(ManufacturerDTO manufacturerDTO) {
       return modelMapper.map(manufacturerDTO, Manufacturer.class);
    }

    public ManufacturerDTO convertToManufacturerDTO(Manufacturer manufacturer) {
        return modelMapper.map(manufacturer, ManufacturerDTO.class);
    }

    @Transactional
    public void editManufacturer(Manufacturer manufacturer, int id) {
        manufacturer.setManufacurerId(id);
        manufacturerRepository.save(manufacturer);
    }

    @Transactional
    public void deleteManufacturer(int id) {
        manufacturerRepository.deleteById(id);
    }


}
