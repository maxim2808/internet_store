package com.example.internet_store.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.Date;

public class PersoneDTO {

        @Email
        @NotNull
        String email;

        @Column(name = "password")
        String password;

        String repeatPassword;

    @Size(min = 6, max = 20, message ="Пароль должен быть от 6 до 20 знаков")
    String fakePassword;

        @Size(min = 10, max = 15, message = "Номер телефона должен быть от 10 до 15 символов")
        @Pattern(regexp = "\\+\\d+", message = "Номер телефона должен начинаться на '+'а затем должны идти цифры без пробелов и других знаков")
        String phoneNumber;

        @Size(min = 3, max = 20, message = "Длина имени пользователя должна быть от 3 до 20 символов")
        String username;

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

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }

    public String getFakePassword() {
        return fakePassword;
    }

    public void setFakePassword(String fakePassword) {
        this.fakePassword = fakePassword;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
