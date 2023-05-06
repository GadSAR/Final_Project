package com.backend.ifm.repository;

import com.backend.ifm.entity.Info;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface InfoRepository extends JpaRepository<Info, Long> {
    List<Info> findAll();

    @Query("SELECT i FROM Info i WHERE i.DRIVER_ID = :driverId")
    List<Info> findAllByDriverId(@Param("driverId") UUID driverId);

    @Query("SELECT i FROM Info i WHERE i.DRIVER_ID = :driverId ORDER BY i.TIME DESC")
    List<Info> findLastByDriverId(@Param("driverId") UUID driverId, Pageable pageable);
}
