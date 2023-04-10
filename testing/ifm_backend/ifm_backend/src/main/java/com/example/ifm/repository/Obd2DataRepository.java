package com.example.ifm.repository;

import com.example.ifm.model.Obd2Data;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Obd2DataRepository extends
        JpaRepository<Obd2Data, Long> {
}