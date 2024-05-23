package com.example.internet_store.services;

import com.example.internet_store.dto.GroupDTO;
import com.example.internet_store.models.Group;
import com.example.internet_store.repositories.ProductGroupRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class GroupService {
    final ProductGroupRepository productGroupRepository;
    final ModelMapper modelMapper;
    @Autowired
    public GroupService(ProductGroupRepository productGroupRepository, ModelMapper modelMapper) {
        this.productGroupRepository = productGroupRepository;
        this.modelMapper = modelMapper;
    }

    public List<Group> findAll() {
        return productGroupRepository.findAll();
    }

    public Optional<Group> findById(int id) {
        return productGroupRepository.findById(id);
    }

    public Optional<Group> findByGroupName(String groupName) {
        return productGroupRepository.findByGroupName(groupName);
    }

    @Transactional
    public Group save(Group group) {
        group.setRegistrationDate(new Date());
        return productGroupRepository.save(group);
    }

    public Group convertToGroup(GroupDTO groupDTO) {
        return modelMapper.map(groupDTO, Group.class);
    }


}
