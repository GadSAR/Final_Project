package com.backend.ifm.repository;

import com.backend.ifm.entity.Info;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InfoRepository extends JpaRepository<Info, Long> {
    List<Info> findAll();
}