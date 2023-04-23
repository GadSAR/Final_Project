package com.example.Obd2scannerEmulator.repository;

import com.example.Obd2scannerEmulator.Info;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InfoRepository extends JpaRepository<Info, Long> {
    List<Info> findAll();
}