package com.example.internet_store.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "product_group")
public class ProductGroup {

    @Id
            @GeneratedValue(strategy = GenerationType.IDENTITY)
            @Column(name = "group_id")
    int id;
    @Column(name = "group_name")
    String groupName;

    @OneToMany(mappedBy = "productGroup" )
    List<Product> productList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
