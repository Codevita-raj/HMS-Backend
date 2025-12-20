package com.projecth.hms.wardmanagement.service;

import com.projecth.hms.department.repository.DepartmentRepository;
import com.projecth.hms.shared.enums.WardStatus;
import com.projecth.hms.wardmanagement.dto.ward.WardRequest;
import com.projecth.hms.wardmanagement.dto.ward.WardResponse;
import com.projecth.hms.wardmanagement.entity.Ward;
import com.projecth.hms.wardmanagement.repository.WardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WardService {

    private final WardRepository wardRepository;
    private final DepartmentRepository departmentRepository;

    public WardResponse create(WardRequest request) {

        validateDepartment(request.getDepartmentId());

        Ward ward = new Ward();
        ward.setName(request.getName());
        ward.setType(request.getType());
        ward.setDepartmentId(request.getDepartmentId());
        ward.setStatus(WardStatus.ACTIVE);

        return map(wardRepository.save(ward));
    }

    public WardResponse getById(Long id) {
        return map(getActiveWard(id));
    }

    public List<WardResponse> getAll() {
        return wardRepository.findAll().stream()
                .filter(w -> w.getStatus() == WardStatus.ACTIVE)
                .map(this::map)
                .toList();
    }

    public WardResponse update(Long id, WardRequest request) {

        Ward ward = getActiveWard(id);

        if (request.getDepartmentId() != null) {
            validateDepartment(request.getDepartmentId());
            ward.setDepartmentId(request.getDepartmentId());
        }

        ward.setName(request.getName());
        ward.setType(request.getType());

        return map(wardRepository.save(ward));
    }

    public void deactivate(Long id) {
        Ward ward = getActiveWard(id);
        ward.setStatus(WardStatus.INACTIVE);
        wardRepository.save(ward);
    }

    /* ---------- helpers ---------- */

    private Ward getActiveWard(Long id) {
        Ward ward = wardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ward not found"));

        if (ward.getStatus() == WardStatus.INACTIVE) {
            throw new RuntimeException("Ward is inactive");
        }
        return ward;
    }

    private void validateDepartment(Long deptId) {
        if (!departmentRepository.existsById(deptId)) {
            throw new RuntimeException("Invalid department");
        }
    }

    private WardResponse map(Ward ward) {
        WardResponse r = new WardResponse();
        r.setId(ward.getId());
        r.setName(ward.getName());
        r.setType(ward.getType());
        r.setStatus(ward.getStatus());
        r.setDepartmentId(ward.getDepartmentId());
        return r;
    }
}

