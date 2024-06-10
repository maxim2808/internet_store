package com.example.internet_store.models;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    int productId;
    @Column(name = "product_name")
    String productName;

    @Column(name = "price")
    double price;
    @Column(name = "quantity")
    int quantity;

    @Column(name = "description")
    String description;

    @ManyToOne()
    @JoinColumn(name = "group_id",referencedColumnName = "group_id")
    Group group;


    @ManyToOne()
    @JoinColumn(name = "manufacturer_id", referencedColumnName = "manufacturer_id")
     Manufacturer manufacturer;
    @Column(name = "discount")
    double discount;



@Column(name = "rating")
double rating;
@Column(name = "popular")
boolean popular;
@Column(name = "registration_date")
@Temporal(TemporalType.TIMESTAMP)
Date registrationDate;
    @Column(name = "product_url")
    String productURL;
    @ManyToMany
            @JoinTable(name = "product_picture", joinColumns = @JoinColumn(name ="product_id"),
            inverseJoinColumns = @JoinColumn(name = "picture_id"))
    List<Picture> pictureList;
    @ManyToOne
    @JoinColumn(name = "main_picture", referencedColumnName = "picture_id")
    Picture mainPicture;


    public int getProductId() {
        return productId;
    }

    public void setProductId(int id) {
        this.productId = id;
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



    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
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

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getProductURL() {
        return productURL;
    }

    public void setProductURL(String productURL) {
        this.productURL = productURL;
    }

    public List<Picture> getPictureList() {
        return pictureList;
    }

    public void setPictureList(List<Picture> pictureList) {
        this.pictureList = pictureList;
    }

    public Picture getMainPicture() {
        return mainPicture;
    }

    public void setMainPicture(Picture mainPicture) {
        this.mainPicture = mainPicture;
    }
}
