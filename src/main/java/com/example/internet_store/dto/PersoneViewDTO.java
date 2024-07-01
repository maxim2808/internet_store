package com.example.internet_store.dto;

public class PersoneViewDTO {
    int personeId;
    String email;
    String phoneNumber;
    String stringRegistrationDate;
    String role;
    String username;

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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getStringRegistrationDate() {
        return stringRegistrationDate;
    }

    public void setStringRegistrationDate(String stringRegistrationDate) {
        this.stringRegistrationDate = stringRegistrationDate;
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
}
