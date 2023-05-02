package com.backend.ifm.controller;

import com.backend.ifm.entity.Company;
import com.backend.ifm.entity.Info;
import com.backend.ifm.repository.CompanyRepository;
import com.backend.ifm.repository.InfoRepository;
import com.backend.ifm.repository.UserRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AccountsController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private InfoRepository infoRepository;


    @GetMapping("/get_company_users")
    public ResponseEntity<?> getAllUsersFromCompany(@RequestParam("companyName") String companyName) {
        Company company = companyRepository.findByName(companyName);
        if (company != null) {
            return ResponseEntity.ok(userRepository.findAllByCompaniesContaining(company));
        } else {
            throw new RuntimeException("Company not found");
        }
    }

    @GetMapping("/get_tracks")
    public ResponseEntity<?> getAllTracks() {
        List<Info> info = infoRepository.findAll();
        if (info != null) {
            return ResponseEntity.ok(info);
        } else {
            throw new RuntimeException("Company not found");
        }
    }


}
