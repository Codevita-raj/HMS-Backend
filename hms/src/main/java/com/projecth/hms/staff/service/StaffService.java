package com.projecth.hms.staff.service;

import com.projecth.hms.address.repository.AddressRepository;
import com.projecth.hms.department.repository.DepartmentRepository;
import com.projecth.hms.shared.enums.StaffStatus;
import com.projecth.hms.staff.dto.StaffRequest;
import com.projecth.hms.staff.dto.StaffResponse;
import com.projecth.hms.staff.entity.Staff;
import com.projecth.hms.staff.repository.StaffRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StaffService {

    private final StaffRepository staffRepository;
    private final DepartmentRepository departmentRepository;
    private final AddressRepository addressRepository;


    public StaffResponse createStaff(StaffRequest request) {

        validateDepartment(request.getDepartmentId());
        validateAddress(request.getAddressId());

        Staff staff = new Staff();
        staff.setFirstName(request.getFirstName());
        staff.setLastName(request.getLastName());
        staff.setPhone(request.getPhone());
        staff.setEmail(request.getEmail());
        staff.setStaffType(request.getStaffType());
        staff.setDepartmentId(request.getDepartmentId());
        staff.setAddressId(request.getAddressId());
        staff.setStatus(StaffStatus.ACTIVE);

        return mapToResponse(staffRepository.save(staff));
    }


    public StaffResponse getStaffById(Long id) {
        Staff staff = getActiveStaff(id);
        return mapToResponse(staff);
    }

    public List<StaffResponse> getAllStaff() {
        return staffRepository.findAll()
                .stream()
                .filter(staff -> staff.getStatus() != StaffStatus.INACTIVE)
                .map(this::mapToResponse)
                .toList();
    }


    public StaffResponse updateStaff(Long id, StaffRequest request) {

        Staff staff = getActiveStaff(id);

        validateAddress(request.getAddressId());
        validateDepartment(request.getDepartmentId());

        staff.setFirstName(request.getFirstName());
        staff.setLastName(request.getLastName());
        staff.setPhone(request.getPhone());
        staff.setEmail(request.getEmail());
        staff.setStaffType(request.getStaffType());
        staff.setAddressId(request.getAddressId());
        staff.setDepartmentId(request.getDepartmentId());

        return mapToResponse(staffRepository.save(staff));
    }

    public StaffResponse changeStatus(Long id, StaffStatus status) {

        Staff staff = staffRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Staff not found"));

        staff.setStatus(status);
        return mapToResponse(staffRepository.save(staff));
    }

    public void softDeleteStaff(Long id) {

        Staff staff = staffRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Staff not found"));

        staff.setStatus(StaffStatus.INACTIVE);
        staffRepository.save(staff);
    }

    /* ================= HELPERS ================= */

    private void validateDepartment(Long departmentId) {
        if (!departmentRepository.existsById(departmentId)) {
            throw new RuntimeException("Invalid department id");
        }
    }

    private void validateAddress(Long addressId){
        if(!addressRepository.existsById(addressId)){
            throw  new RuntimeException("Invalid address Id");
        }
    }
    private Staff getActiveStaff(Long id) {
        Staff staff = staffRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Staff not found"));

        if (staff.getStatus() == StaffStatus.INACTIVE) {
            throw new RuntimeException("Staff is inactive");
        }
        return staff;
    }

    private StaffResponse mapToResponse(Staff staff) {
        StaffResponse response = new StaffResponse();
        response.setId(staff.getId());
        response.setFirstName(staff.getFirstName());
        response.setLastName(staff.getLastName());
        response.setPhone(staff.getPhone());
        response.setEmail(staff.getEmail());
        response.setStaffType(staff.getStaffType());
        response.setStatus(staff.getStatus());
        response.setDepartmentId(staff.getDepartmentId());
        response.setAddressId(staff.getAddressId());
        return response;
    }
}


