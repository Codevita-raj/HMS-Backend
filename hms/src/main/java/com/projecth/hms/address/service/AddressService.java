package com.projecth.hms.address.service;

import com.projecth.hms.address.dto.AddressRequest;
import com.projecth.hms.address.dto.AddressResponse;
import com.projecth.hms.address.entity.Address;
import com.projecth.hms.address.repository.AddressRepository;
import com.projecth.hms.user.entity.User;
import com.projecth.hms.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class AddressService {
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

//    @Transactional
//    public Address addAddress(Long userId,AddressRequest addressRequest) {
//
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new RuntimeException("User Not Found"));
//
//        Address address = new Address();
//        address.setCity(addressRequest.getCity());
//        address.setStreet(addressRequest.getStreet());
//        address.setState(addressRequest.getState());
//        address.setCountry(addressRequest.getCountry());
//        address.setLandmark(addressRequest.getLandmark());
//        address.setHouseNumber(addressRequest.getHouseNumber());
//        address.setPinCode(addressRequest.getPinCode());
//       // address.setUser(user);
//        return addressRepository.save(address);
//      //  Address savedAddress = addressRepository.save(address);
//      //  return mapToResponse(savedAddress);
//    }
//
//    public List<AddressResponse> getAddressByUserId(Long id) {
//        List<Address> addresses = addressRepository.findByUserId(id);
//        if (addresses.isEmpty()) {
//            throw new RuntimeException("No addresses found for userId " + id);
//        }
//        return addresses.stream()
//                .map(this::mapToResponse)
//                .toList();
//    }
//
//    @Transactional
//    public AddressResponse updateAddress(Long userId,Long addressId,AddressRequest addressRequest){
//
//      Address updatedAddress = addressRepository.findById(addressId)
//              .orElseThrow(() -> new RuntimeException("Address not found with id "+addressId));
////
////        if(!updatedAddress.getUser().getId().equals(userId)){
////           throw new RuntimeException("Address id "+addressId+" does not belong to the userId "+userId );
////      }
//
//        updatedAddress.setCity(addressRequest.getCity());
//        updatedAddress.setStreet(addressRequest.getStreet());
//        updatedAddress.setState(addressRequest.getState());
//        updatedAddress.setCountry(addressRequest.getCountry());
//        updatedAddress.setLandmark(addressRequest.getLandmark());
//        updatedAddress.setHouseNumber(addressRequest.getHouseNumber());
//        updatedAddress.setPinCode(addressRequest.getPinCode());
//        return addressRepository.save(address);
//
////        Address savedAddress = addressRepository.save(updatedAddress);
////
////        return mapToResponse(savedAddress);
//
//    }
//
//    @Transactional
//    public void deleteAddress(Long userId,Long addressId){
//        Address address = addressRepository.findById(addressId)
//                .orElseThrow(() -> new RuntimeException("Address not found with id "+addressId));
//
//        if(!address.getUser().getId().equals(userId)){
//            throw new RuntimeException("Address id "+addressId+" does not belong to the userId "+userId );
//        }
//        addressRepository.delete(address);
//    }
//
//    //helper method
//    private AddressResponse mapToResponse(Address address) {
//        return AddressResponse.builder()
//                .id(address.getId())
//                .city(address.getCity())
//                .state(address.getState())
//                .country(address.getCountry())
//                .houseNumber(address.getHouseNumber())
//                .street(address.getStreet())
//                .pinCode(address.getPinCode())
//                .landmark(address.getLandmark())
//                .userId(address.getUser().getId())
//                .build();
//    }


@Transactional
public Address addAddress(AddressRequest req) {
    Address address = new Address();
    address.setHouseNumber(req.getHouseNumber());
    address.setStreet(req.getStreet());
    address.setCity(req.getCity());
    address.setState(req.getState());
    address.setCountry(req.getCountry());
    address.setPinCode(req.getPinCode());
    address.setLandmark(req.getLandmark());
    return addressRepository.save(address);
}

    @Transactional
    public Address updateAddress(Long addressId, AddressRequest req) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found"));

        address.setHouseNumber(req.getHouseNumber());
        address.setStreet(req.getStreet());
        address.setCity(req.getCity());
        address.setState(req.getState());
        address.setCountry(req.getCountry());
        address.setPinCode(req.getPinCode());
        address.setLandmark(req.getLandmark());

        return addressRepository.save(address);
    }

    public Address get(Long addressId) {
        return addressRepository.findById(addressId).orElse(null);
    }
}
