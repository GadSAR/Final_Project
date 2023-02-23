package com.example.fleetmanagement.repository;

import com.example.fleetmanagement.entity.Info;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FleetManagementRepository extends JpaRepository<Info, Long> {
}