package com.example.internet_store.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "product_group")
public class Group {

    @Id
            @GeneratedValue(strategy = GenerationType.IDENTITY)
            @Column(name = "group_id")
    int groupId;
    @Column(name = "group_name")
    @NotEmpty(message = "Группа не должна быть пустой")
    String groupName;

    @OneToMany(mappedBy = "group")
    List<Product> productList;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="registration_date")
    Date registrationDate;
    @Column(name = "product_URL")

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int id) {
        this.groupId = id;
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

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }


}
