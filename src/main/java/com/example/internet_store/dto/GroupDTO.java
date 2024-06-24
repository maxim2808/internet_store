package com.example.internet_store.dto;

import com.example.internet_store.models.Product;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public class GroupDTO {

   // @Column(name = "group_name")
    int groupId;
    @NotEmpty(message = "Группа не должна быть пустой")
    String groupName;
    List<Product> productList;

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }
}
