package com.backend.ifm.repository;

import com.backend.ifm.entity.Company;
import com.backend.ifm.entity.Role;
import com.backend.ifm.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    List<User> findAll();

    List<User> findAllByCompaniesContaining(Company company);
    @Transactional
    void deleteUserByEmail(String email);

    @Query("SELECT u FROM User u JOIN u.roles r JOIN u.companies c WHERE u.email = :email AND r.name = 'ROLE_ADMIN' AND c.id IN (SELECT c2.id FROM User u2 JOIN u2.companies c2 WHERE u2.email = :email)")
    User findAdminByEmailAndCompany(@Param("email") String email);

    List<User> findAllByCompaniesContains(Company company);

    }
