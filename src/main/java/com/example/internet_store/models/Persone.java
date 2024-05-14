package com.example.internet_store.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "persone")
public class Persone {
    @Id
            @GeneratedValue(strategy = GenerationType.IDENTITY)
            @Column(name = "persone_id")
    int id;

    @Email
            @NotNull
            @Column(name = "email")
    String email;

    @Column(name = "password")
    String password;
    @Transient
    String repeatPassword;

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }

    @Column(name = "phone_number")
    String phoneNumber;
    @Column(name = "registration_date")
            @Temporal(TemporalType.TIMESTAMP)
    Date registrationDate;
    @Column(name = "role")
    String role;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
