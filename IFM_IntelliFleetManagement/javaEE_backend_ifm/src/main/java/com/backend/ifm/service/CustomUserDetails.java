package com.backend.ifm.service;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

public class CustomUserDetails extends org.springframework.security.core.userdetails.User {

    private final List<GrantedAuthority> companies;
    private final String name;


    public CustomUserDetails(String name, String username, String password, Collection<? extends GrantedAuthority> authorities, List<GrantedAuthority> companies) {
        super(username, password, authorities);
        this.companies = companies;
        this.name = name;
    }

    public List<GrantedAuthority> getCompanies() {
        return companies;
    }
    public String getName() {
        return name;
    }

}