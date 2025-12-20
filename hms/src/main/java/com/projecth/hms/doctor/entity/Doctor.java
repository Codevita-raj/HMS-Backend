package com.projecth.hms.doctor.entity;

import com.projecth.hms.shared.enums.DoctorStatus;
import com.projecth.hms.shared.enums.Specialization;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "doctors")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;

    @Enumerated(EnumType.STRING)
    private Specialization specialization;

    private String phone;
    private String email;
    private Integer experience;

    @Enumerated(EnumType.STRING)
    private DoctorStatus status;

    @Column(name = "address_id")
    private Long addressId;

    private Long departmentId;
}

