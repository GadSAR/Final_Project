package com.backend.ifm.controller;

import com.backend.ifm.entity.Company;
import com.backend.ifm.entity.User;
import com.backend.ifm.repository.CompanyRepository;
import com.backend.ifm.repository.UserRepository;
import com.backend.ifm.service.AccountsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AccountsController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CompanyRepository companyRepository;


    @GetMapping("/get_company_users")
    public ResponseEntity<?> getAllUsersFromCompany(@RequestParam("companyName") String companyName) {
        Company company = companyRepository.findByName(companyName);
        if (company != null) {
            return ResponseEntity.ok(userRepository.findAllByCompaniesContaining(company));
        } else {
            throw new RuntimeException("Company not found");
        }
    }


}
