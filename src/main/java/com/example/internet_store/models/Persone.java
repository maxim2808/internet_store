package com.example.internet_store.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "persone")
public class Persone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "persone_id")
    int personeId;

    @Email
    @NotNull
    @Column(name = "email")
    String email;


//
//    @Transient
//    @Size(min = 6, max = 20, message ="Пароль должен быть от 6 до 20 знаков")
//    String fakePassword;

    @Column(name = "password")

    String password;
//    @Transient
//    String repeatPassword;



    @Column(name = "phone_number")
            @Size(min = 10, max = 15, message = "Номер телефона должен быть от 10 до 15 символов")
            @Pattern(regexp = "\\+\\d+", message = "Номер телефона должен начинаться на '+'а затем должны идти цифры без пробелов и других знаков")
    String phoneNumber;
    @Column(name = "registration_date")
    @Temporal(TemporalType.TIMESTAMP)
    Date registrationDate;
    @Transient
    String stringRegistrationDate;
    @Column(name = "role")
    String role;
    @Column(name = "username")
    @Size(min = 3, max = 20, message = "Длина имени пользователя должна быть от 3 до 20 символов")
    String username;
    @OneToMany(mappedBy = "persone" )
    List<Order> orderList;




    public int getPersoneId() {
        return personeId;
    }

    public void setPersoneId(int personeId) {
        this.personeId = personeId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStringRegistrationDate() {
        if(registrationDate != null){
            stringRegistrationDate = registrationDate.toString();
        }
        return stringRegistrationDate;
    }

    public void setStringRegistrationDate(String stringRegistrationDate) {
        this.stringRegistrationDate = stringRegistrationDate;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }
}
