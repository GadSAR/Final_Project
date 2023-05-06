package com.backend.ifm.controller;

import com.backend.ifm.entity.Company;
import com.backend.ifm.entity.Info;
import com.backend.ifm.entity.Role;
import com.backend.ifm.entity.User;
import com.backend.ifm.repository.CompanyRepository;
import com.backend.ifm.repository.InfoRepository;
import com.backend.ifm.repository.RoleRepository;
import com.backend.ifm.repository.UserRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class DataController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private InfoRepository infoRepository;
    @Autowired
    private RoleRepository roleRepository;


    @GetMapping("/get_company_users")
    public ResponseEntity<?> getAllUsersFromCompany(@RequestParam("companyName") String companyName) {
        Company company = companyRepository.findByName(companyName);
        if (company != null) {
            List<User> users = userRepository.findAllByCompaniesContaining(company);
            List<User> filteredUsers = users.stream()
                    .filter(user -> user.getRoles().stream().anyMatch(role -> role.getName().equals("ROLE_USER")))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(filteredUsers);
        } else {
            throw new RuntimeException("Company not found");
        }
    }

    @GetMapping("/get_all_tracks")
    public ResponseEntity<?> getAllTracks() {
        List<Info> info = infoRepository.findAll();
        if (info != null) {
            return ResponseEntity.ok(info);
        } else {
            throw new RuntimeException("Company not found");
        }
    }

    @GetMapping("/get_user_tracks")
    public ResponseEntity<?> getUserTracks(@RequestParam("driverEmail") String driverEmail) {
        User driver = userRepository.findByEmail(driverEmail);
        if (driver != null) {
            List<Info> info = infoRepository.findAllByDriverId(driver.getId());
            return ResponseEntity.ok(info);
        } else {
            throw new RuntimeException("Driver not found");
        }
    }

    @GetMapping("/get_all_cars")
    public ResponseEntity<?> getAllCars() {
        List<String> vehicleIds = infoRepository.findAll()
                .stream()
                .map(Info::getVEHICLE_ID)
                .distinct()
                .collect(Collectors.toList());
        return ResponseEntity.ok(vehicleIds);
    }

    @GetMapping("/get_user_cars")
    public ResponseEntity<List<String>> getDriverCars(@RequestParam("driverEmail") String driverEmail) {
        User driver = userRepository.findByEmail(driverEmail);
        if (driver != null) {
            List<Info> info = infoRepository.findAllByDriverId(driver.getId());
            List<String> vehicleIds = info.stream()
                    .map(Info::getVEHICLE_ID)
                    .distinct()
                    .collect(Collectors.toList());
            return ResponseEntity.ok(vehicleIds);
        } else {
            throw new RuntimeException("Driver not found");
        }
    }

    @GetMapping("/get_user_lastdrive")
    public ResponseEntity<?> getLastDrive(@RequestParam("email") String email) {
        if (userRepository.findByEmail(email) != null) {
            List<Info> info = infoRepository.findLastByDriverId(userRepository.findByEmail(email).getId(), PageRequest.of(0, 1));
            return ResponseEntity.ok(info);
        } else {
            throw new RuntimeException("Driver not found");
        }
    }

    @GetMapping("/get_user_managermail")
    public ResponseEntity<String> getManagerMail(@RequestParam("email") String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            Company company = user.getCompanies().get(0);
            List<User> users = userRepository.findAllByCompaniesContains(company);
            List<User> admin = users.stream()
                    .filter(u -> u.getRoles().stream().anyMatch(role -> role.getName().equals("ROLE_ADMIN")))
                    .toList();
            String adminEmail = admin.get(0).getEmail();
            return ResponseEntity.ok("\"" + adminEmail + "\"");
        } else {
            throw new RuntimeException("User not found");
        }
    }


}
