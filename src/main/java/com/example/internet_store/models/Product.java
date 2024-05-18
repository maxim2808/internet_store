package com.example.internet_store.models;

import jakarta.persistence.*;
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    int id;
    @Column(name = "product_name")
    String itemName;

    @Column(name = "price")
    double price;
    @Column(name = "quantity")
    int quantity;

    @Column(name = "description")
    String description;

    @ManyToOne()
    @JoinColumn(name = "group_name",referencedColumnName = "group_name")
    ProductGroup productGroup;

    @Column(name = "manufacturer")
    String manufacturer;
    @Column(name = "discount")
    double discount;

    @Transient
   // double finalPrice = price-(price*(quantity/100));
    double finalPrice = price-(price*(quantity/100));

@Column(name = "rating")
    double rating;
@Column(name = "popular")
boolean popular;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ProductGroup getProductGroup() {
        return productGroup;
    }

    public void setProductGroup(ProductGroup productGroup) {
        this.productGroup = productGroup;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getFinalPrice() {
        return getPrice();
    }

    public void setFinalPrice(double finalPricre) {
        this.finalPrice = finalPrice;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public boolean isPopular() {
        return popular;
    }

    public void setPopular(boolean popular) {
        this.popular = popular;
    }
}
