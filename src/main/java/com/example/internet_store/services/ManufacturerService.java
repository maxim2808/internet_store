package com.example.internet_store.services;

import com.example.internet_store.dto.ManufacturerDTO;
import com.example.internet_store.models.Manufacturer;
import com.example.internet_store.models.mapper.ManufacturerMapper;
import com.example.internet_store.repositories.ManufacturerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(readOnly = true)
public class ManufacturerService  {
    final ManufacturerRepository manufacturerRepository;
    final ModelMapper modelMapper;
    final JdbcTemplate jdbcTemplate;

    @Autowired
    public ManufacturerService(ManufacturerRepository manufacturerRepository, ModelMapper modelMapper, JdbcTemplate jdbcTemplate) {
        this.manufacturerRepository = manufacturerRepository;
        this.modelMapper = modelMapper;
        this.jdbcTemplate = jdbcTemplate;
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

    public List<Manufacturer> getAllManufacturersByGroup(int groupId) {

        return jdbcTemplate.query("SELECT DISTINCT manufacturer.manufacturer_id, manufacturer.manufacturer_name, manufacturer.registration_date FROM manufacturer " +
                "JOIN product ON product.manufacturer_id = manufacturer.manufacturer_id " +
                "JOIN product_group ON product.group_id = product_group.group_id " +
                "WHERE product_group.group_id = ?", new Object[]{groupId}, new ManufacturerMapper());
    }


    public Set<String> getAllManufacturerNames(String name) {

        Set<String> manufacturerNames = new LinkedHashSet<>();
        if (name != null&&!name.isBlank()&&!name.equals("Все производители")) {
        manufacturerNames.add(name);
        }
        manufacturerNames.add("Все производители");
    for(Manufacturer manufacturer : manufacturerRepository.findAll()){
        manufacturerNames.add(manufacturer.getManufacturerName());
    }
        return manufacturerNames;
    }

}
