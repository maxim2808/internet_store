package com.example.internet_store.dto;

import com.example.internet_store.models.Group;
import com.example.internet_store.models.Manufacturer;
import com.example.internet_store.models.Picture;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

public class ProductDTO {
    int productId;
    @NotEmpty(message = "Поле не должно быть пустым")
            @Size(min = 2, max = 300, message = "Минимальный размер имени 2, максимальный 300")
    String productName;
    @NotNull(message = "Поле не должно быть пустым")
    @DecimalMin(value = "0.01", message = "Минимальное значение должно быть минимум 0,01")
    double price;
    @Min(value = 0, message = "Минимальное количество 0")
    int quantity;

    @Size(max = 1000, message = "Описание должно быть не больше 1000 символов")
    String description;

//    @ManyToOne()
//    @JoinColumn(name = "group_id",referencedColumnName = "group_id")
    Group group;


//    @ManyToOne()
//    @JoinColumn(name = "manufacturer_id", referencedColumnName = "manufacturer_id")
  //  @NotEmpty(message = "Поле не должно быть пустым")
    Manufacturer manufacturer;
    @Max(value = 1, message = "Максимум 1")
    double discount;
    // double finalPrice = price-(price*(quantity/100));
    double finalPrice = price-(price*(discount/100));
    @Min(value = 0, message = "Рейтигш не может быть меньше 0")
            @Max(value = 5, message = "Рейтинг не может быть больше 5,0")
    double rating;
    boolean popular;
   // @NotBlank(message = "Поле не должно быть пустым")
    String productURL;

    Picture mainPicture;
    String addressPicture;
    String similarProductName;


    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
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

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getFinalPrice() {
        return price-(price*(discount/100));
     //   return finalPrice;
    }

    public void setFinalPrice(double finalPrice) {
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

    public String getProductURL() {
        return productURL;
    }

    public void setProductURL(String productURL) {
        this.productURL = productURL;
    }

    public Picture getMainPicture() {
        return mainPicture;
    }

    public void setMainPicture(Picture mainPicture) {
        this.mainPicture = mainPicture;
    }

    public String getAddressPicture() {
        return addressPicture;
    }

    public void setAddressPicture(String addressPicture) {
        this.addressPicture = addressPicture;
    }

    public String getSimilarProductName() {
        return similarProductName;
    }

    public void setSimilarProductName(String similarProductName) {
        this.similarProductName = similarProductName;
    }
}
