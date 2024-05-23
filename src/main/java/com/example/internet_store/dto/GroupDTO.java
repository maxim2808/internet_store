package com.example.internet_store.dto;

import com.example.internet_store.models.Product;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public class GroupDTO {

   // @Column(name = "group_name")
    @NotEmpty(message = "Группа не должна быть пустой")
    String groupName;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    //    @OneToMany(mappedBy = "group")
//    List<Product> productList;
}
