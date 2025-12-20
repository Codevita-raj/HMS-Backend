package com.projecth.hms.address.entity;

import com.projecth.hms.patient.entity.Patient;
import com.projecth.hms.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String houseNumber;
    private String street;
    private String city;
    private String state;
    private String pinCode;
    private String country;
    private String landmark;

//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user;
//    @OneToMany(mappedBy = "address",cascade = CascadeType.ALL,orphanRemoval = true)
//    private Patient patient;
}
