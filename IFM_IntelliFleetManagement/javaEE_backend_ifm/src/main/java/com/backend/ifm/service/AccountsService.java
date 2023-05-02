package com.backend.ifm.service;

import com.backend.ifm.dto.UpdateUserRequest;
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
    private PasswordEncoder passwordEncoder;


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

    public User updateUser(UpdateUserRequest updateUserRequest) {
        User user = userRepository.findByEmail(updateUserRequest.getEmail());
        if (user != null) {
            if (!updateUserRequest.getUsername().isEmpty()) {
                user.setName(updateUserRequest.getUsername());
            }
            if (!updateUserRequest.getCompany().isEmpty()) {
                Company company = companyRepository.findByName(user.getCompany());
                company.setName(updateUserRequest.getCompany());
                companyRepository.save(company);
            }
            if (passwordEncoder.matches(updateUserRequest.getC_password(), user.getPassword())) {
                user.setPassword(passwordEncoder.encode(updateUserRequest.getN_password()));
            }
            userRepository.save(user);
            return user;
        }
        return null;
    }

    public void UpdateUserByAdmin(UpdateUserRequest updateUserRequest) {
        User user = userRepository.findByEmail(updateUserRequest.getEmail());
        if (user != null) {
            if ((!(updateUserRequest.getN_email() == null)) && (userRepository.findByEmail(updateUserRequest.getN_email()) == null)) {
                user.setEmail(updateUserRequest.getN_email());
            }
            if (!(updateUserRequest.getUsername() == null)) {
                user.setName(updateUserRequest.getUsername());
            }
            if (!(updateUserRequest.getN_password() == null)) {
                user.setPassword(passwordEncoder.encode(updateUserRequest.getN_password()));
            }
            userRepository.save(user);
        }
    }

    public void deleteUserByEmail(String email) {
        User userToDelete = userRepository.findByEmail(email);
        if (userToDelete != null) {
            userRepository.deleteUserByEmail(email);
        }
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
