package com.example.internet_store.services;

import com.example.internet_store.dto.GroupDTO;
import com.example.internet_store.models.Group;
import com.example.internet_store.models.mapper.GroupMapper;
import com.example.internet_store.repositories.ProductGroupRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
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
    final JdbcTemplate jdbcTemplate;
    @Autowired
    public GroupService(ProductGroupRepository productGroupRepository, ModelMapper modelMapper, JdbcTemplate jdbcTemplate) {
        this.productGroupRepository = productGroupRepository;
        this.modelMapper = modelMapper;
        this.jdbcTemplate = jdbcTemplate;
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

    public Optional<Group> findByURL(String url) {
        return productGroupRepository.findByGroupURL(url);
    }

    @Transactional
    public void save(Group group) {
        group.setRegistrationDate(new Date());
      productGroupRepository.save(group);
    }

    @Transactional
    public void edit(Group group, int id) {
        group.setGroupId(id);
        productGroupRepository.save(group);
    }
    @Transactional
    public void deleteById(int id) {
        productGroupRepository.deleteById(id);
    }

    public Group convertToGroup(GroupDTO groupDTO) {
        return modelMapper.map(groupDTO, Group.class);
    }

    public GroupDTO convertToDTO(Group group) {
        return modelMapper.map(group, GroupDTO.class);
    }

    public List<Group> findJDBCGroup(){
        return jdbcTemplate.query("select * from product_group", new GroupMapper());
    }

//    public int countProductInGroup(GroupDTO groupDTO){
//       return groupDTO.getProductList().size();
//    }






}
