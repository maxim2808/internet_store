package com.example.internet_store.models;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    int id;
    @Column(name = "product_name")
    String productName;

    @Column(name = "price")
    double price;
    @Column(name = "quantity")
    int quantity;

    @Column(name = "description")
    String description;

    @ManyToOne()
    @JoinColumn(name = "group_name",referencedColumnName = "group_name")
    Group group;

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
@Column(name = "image")
byte [] image;
@Column(name = "registration_date")
@Temporal(TemporalType.TIMESTAMP)
Date registrationDate;

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String itemName) {
        this.productName = itemName;
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

    public Group getProductGroup() {
        return group;
    }

    public void setProductGroup(Group group) {
        this.group = group;
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

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }
}
