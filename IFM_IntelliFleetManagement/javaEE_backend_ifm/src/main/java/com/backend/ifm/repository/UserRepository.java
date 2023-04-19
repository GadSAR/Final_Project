package com.backend.ifm.repository;

import com.backend.ifm.entity.Company;
import com.backend.ifm.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    List<User> findAll();
    List<User> findAllByCompaniesContaining(Company company);
    @Transactional
    void deleteUserByEmail(String email);

}
