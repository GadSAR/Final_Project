package com.example.Obd2scannerEmulator;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.persistence.*;
import java.time.LocalDateTime;

@Component
// @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Entity
@Table(name = "obd2scannerdata")
@Lazy
@Getter
@Setter
public class Info {

    @PostConstruct
    private void postConstruct() {
        System.out.println("Bean created");
    }

    @PreDestroy
    public void preDestroy() {
        System.out.println("Bean Destroyed");
    }

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
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