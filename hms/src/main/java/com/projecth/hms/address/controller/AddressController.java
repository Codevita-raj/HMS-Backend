package com.projecth.hms.address.controller;

import com.projecth.hms.address.dto.AddressRequest;
import com.projecth.hms.address.dto.AddressResponse;
import com.projecth.hms.address.service.AddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class AddressController {

    private final AddressService addressService;

//    @PostMapping("/users/{userId}/addresses")
//    public ResponseEntity<AddressResponse> addAddress( @PathVariable Long userId, @RequestBody @Valid AddressRequest addressRequest){
//        AddressResponse address = addressService.addAddress(addressRequest);
//        return ResponseEntity.ok(address);
//    }
//    @GetMapping("/users/{userId}/addresses")
//    public ResponseEntity<List<AddressResponse>> getAddressByUserId(@PathVariable Long userId){
//        List<AddressResponse> addressResponses = addressService.getAddressByUserId(userId);
//        return  ResponseEntity.ok(addressResponses);
//    }
//    @PutMapping("/users/{userId}/addresses/{addressId}")
//    public ResponseEntity<AddressResponse> updateAddress(@PathVariable Long userId, @PathVariable Long addressId, @RequestBody @Valid AddressRequest addressRequest){
//        AddressResponse updatedAddress = addressService.updateAddress(userId,addressId,addressRequest);
//        return ResponseEntity.ok(updatedAddress);
//    }
//    @DeleteMapping("/users/{userId}/addresses/{addressId}")
//    public ResponseEntity<String> deleteAddress(@PathVariable Long userId,@PathVariable Long addressId){
//         addressService.deleteAddress(userId,addressId);
//         return ResponseEntity.ok("Address deleted successfully");
//    }
 }
