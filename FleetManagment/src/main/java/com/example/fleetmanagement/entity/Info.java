package com.example.fleetmanagement.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Info {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column
    LocalDateTime time;

    @Column
    private String IP;

    @Column
    private String VEHICLE_ID;

    @Column
    private int SPEED;

    @Column
    private double THROTTLE_POS;

    @Column
    private int ENGINE_RPM;

    @Column
    private double ENGINE_LOAD;

    @Column
    private int ENGINE_COOLANT_TEMP;

    @Column
    private int INTAKE_MANIFOLD_PRESSURE;

    @Column
    private double MAF;

    @Column
    private double FUEL_LEVEL;

    @Column
    private int FUEL_PRESSURE;

    @Column
    private double TIMING_ADVANCE;

    @Column
    private String TROUBLE_CODES;

}
