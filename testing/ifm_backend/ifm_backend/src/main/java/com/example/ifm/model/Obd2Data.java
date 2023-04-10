package com.example.ifm.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Obd2Data {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false)
    private Long ID;

    @Column
    LocalDateTime TIME;

    @Column
    private String IP;

    @Column
    private String VEHICLE_ID;

    @Column
    private Integer SPEED;

    @Column
    private Double THROTTLE_POS;

    @Column
    private Integer ENGINE_RPM;

    @Column
    private Double ENGINE_LOAD;

    @Column
    private Integer ENGINE_COOLANT_TEMP;

    @Column
    private Integer INTAKE_MANIFOLD_PRESSURE;

    @Column
    private Double MAF;

    @Column
    private Double FUEL_LEVEL;

    @Column
    private Integer FUEL_PRESSURE;

    @Column
    private Double TIMING_ADVANCE;

    @Column
    private String TROUBLE_CODES;

    @Column
    private Byte ISSUES;

}
