package com.projecth.hms.address.dto;

import com.projecth.hms.user.entity.User;
import lombok.Data;

@Data
public class AddressRequest {
    private Long userId;
    private String houseNumber;
    private String street;
    private String city;
    private String state;
    private String pinCode;
    private String country;
    private String landmark;

}
