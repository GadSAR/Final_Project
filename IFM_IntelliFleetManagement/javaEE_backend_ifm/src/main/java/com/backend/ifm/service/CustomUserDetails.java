package com.backend.ifm.service;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

public class CustomUserDetails extends org.springframework.security.core.userdetails.User {

    private final List<GrantedAuthority> companies;

    public CustomUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities, List<GrantedAuthority> companies) {
        super(username, password, authorities);
        this.companies = companies;
    }

    public List<GrantedAuthority> getCompanies() {
        return companies;
    }

}