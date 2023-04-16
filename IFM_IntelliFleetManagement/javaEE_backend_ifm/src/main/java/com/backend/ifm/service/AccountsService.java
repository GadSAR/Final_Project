package com.backend.ifm.service;

import com.backend.ifm.entity.Company;
import com.backend.ifm.entity.Role;
import com.backend.ifm.entity.User;
import com.backend.ifm.repository.CompanyRepository;
import com.backend.ifm.repository.RoleRepository;
import org.springframework.stereotype.Service;
import com.backend.ifm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Service
public class AccountsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    PasswordEncoder passwordEncoder;


    public void createAccount(String name, String company, String email, String password, String roleName) {
        Company companyEntity = getOrCreateCompany(company);
        Role roleEntity = getOrCreateRole(roleName);
        User user = new User(
                name,
                email,
                passwordEncoder.encode(password),
                List.of(roleEntity),
                List.of(companyEntity));
        userRepository.save(user);
    }

    public void createUser(String name, String company, String email, String password) {
        createAccount(name, company, email, password, "ROLE_USER");
    }

    public void createAdmin(String name, String company, String email, String password) {
        createAccount(name, company, email, password, "ROLE_ADMIN");
    }

    private Company getOrCreateCompany(String companyName) {
        Company company = companyRepository.findByName(companyName);
        if (company == null) {
            company = new Company(companyName);
            companyRepository.save(company);
        }
        return company;
    }

    private Role getOrCreateRole(String roleName) {
        Role role = roleRepository.findByName(roleName);
        if (role == null) {
            role = new Role(roleName);
            roleRepository.save(role);
        }
        return role;
    }

}
