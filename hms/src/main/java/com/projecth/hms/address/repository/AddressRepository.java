package com.projecth.hms.address.repository;

import com.projecth.hms.address.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address,Long> {
  //  Address findByUserId(Long id);
}
