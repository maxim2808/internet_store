package com.example.internet_store.services;

import com.example.internet_store.models.Group;
import com.example.internet_store.repositories.ProductGroupRepository;
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
    @Autowired
    public GroupService(ProductGroupRepository productGroupRepository) {
        this.productGroupRepository = productGroupRepository;
    }

    public List<Group> findAll() {
        return productGroupRepository.findAll();
    }

    public Optional<Group> findByGroupName(String groupName) {
        return productGroupRepository.findByGroupName(groupName);
    }

    @Transactional
    public Group save(Group group) {
        group.setRegistrationDate(new Date());
        return productGroupRepository.save(group);
    }


}
