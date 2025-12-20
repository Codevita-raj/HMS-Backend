package com.projecth.hms.shared.mapper;

import com.projecth.hms.address.dto.AddressResponse;
import com.projecth.hms.address.entity.Address;

public class AddressMapper {

    private AddressMapper() {} // utility class

    public static AddressResponse toResponse(Address address) {
        if (address == null) return null;

        return AddressResponse.builder()
                .id(address.getId())
                .houseNumber(address.getHouseNumber())
                .street(address.getStreet())
                .city(address.getCity())
                .state(address.getState())
                .country(address.getCountry())
                .pinCode(address.getPinCode())
                .landmark(address.getLandmark())
                .build();
    }
}

