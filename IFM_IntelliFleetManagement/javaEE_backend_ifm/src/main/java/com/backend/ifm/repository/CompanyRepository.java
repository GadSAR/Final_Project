package com.backend.ifm.repository;

import com.backend.ifm.entity.Company;
import com.backend.ifm.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CompanyRepository extends JpaRepository<Company, UUID> {
    Company findByName(String name);

}
