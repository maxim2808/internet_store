package com.example.internet_store.security;

import com.example.internet_store.models.Persone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.List;


public class PersoneDetail implements UserDetails {

    final Persone persone;

    public PersoneDetail(Persone persone) {
        this.persone = persone;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(persone.getRole()));
    }

    @Override
    public String getPassword() {
        return persone.getPassword();
    }

    @Override
    public String getUsername() {
        return persone.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Persone getPersone() {
        return persone;
    }

    @Override
    public String toString() {
        return "PersoneDetail{" +
                "persone=" + persone +
                '}';
    }
}
