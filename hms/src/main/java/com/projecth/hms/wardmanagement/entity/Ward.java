package com.projecth.hms.wardmanagement.entity;

import com.projecth.hms.shared.enums.WardStatus;
import com.projecth.hms.shared.enums.WardType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "wards")
@Getter
@Setter
public class Ward {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private WardType type;

    @Enumerated(EnumType.STRING)
    private WardStatus status;

    private Long departmentId;
}

