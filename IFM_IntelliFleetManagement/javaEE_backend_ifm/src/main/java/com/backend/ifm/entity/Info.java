package com.backend.ifm.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
// @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Table(name = "obd2scannerdata2")
@Lazy
@Getter
@Setter
@Entity
public class Info {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(nullable = false)
    private Long ID;

    @Column(columnDefinition = "BINARY(16)", nullable = false)
    private UUID DRIVER_ID;

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