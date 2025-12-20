package com.projecth.hms.patient.entity;

import com.projecth.hms.address.entity.Address;
import com.projecth.hms.shared.enums.Gender;
import com.projecth.hms.shared.enums.PatientStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "patients")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Business-friendly patient code, starts from 10001
    @Column(nullable = true, unique = true)
    private String patientCode;

    @Column(nullable = false)
    private String firstName;

    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    private LocalDate dob;

    private String email;

    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PatientStatus status;

    private Long addressId;

    private Long assignedDoctorId; // can be Staff/Doctor reference
}

