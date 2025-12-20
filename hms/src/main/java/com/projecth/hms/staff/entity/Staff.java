package com.projecth.hms.staff.entity;

import com.projecth.hms.shared.enums.StaffStatus;
import com.projecth.hms.shared.enums.StaffType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "staff")
@Getter
@Setter
public class Staff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String phone;
    private String email;

    @Enumerated(EnumType.STRING)
    private StaffType staffType;

    @Enumerated(EnumType.STRING)
    private StaffStatus status;

    private Long departmentId;
    private Long addressId;
}

