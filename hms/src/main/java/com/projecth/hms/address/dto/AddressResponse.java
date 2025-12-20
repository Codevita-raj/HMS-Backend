package com.projecth.hms.address.dto;

import com.projecth.hms.user.entity.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddressResponse {
    private Long id;
    private String houseNumber;
    private String street;
    private String city;
    private String state;
    private String pinCode;
    private String country;
    private String landmark;
    private Long userId;
}
