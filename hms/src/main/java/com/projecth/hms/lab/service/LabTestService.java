package com.projecth.hms.lab.service;

import com.projecth.hms.lab.dto.labTest.LabTestRequest;
import com.projecth.hms.lab.dto.labTest.LabTestResponse;
import com.projecth.hms.lab.entity.LabTest;
import com.projecth.hms.lab.repository.LabTestRepository;
import com.projecth.hms.user.entity.User;
import com.projecth.hms.user.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LabTestService {

    private final LabTestRepository labTestRepository;
    private final UserService userService;

    @Transactional
    public LabTestResponse createLabTest(LabTestRequest request) {
        User currentUser = userService.getCurrentUser();
        if (labTestRepository.existsByTestCode(request.getTestCode())) {
            throw new RuntimeException("Lab test already exists with code " + request.getTestCode());
        }

        LabTest test = new LabTest();
        test.setTestCode(request.getTestCode());
        test.setTestName(request.getTestName());
        test.setTestType(request.getTestType());
        test.setPrice(request.getPrice());
        test.setDepartmentId(request.getDepartmentId());
        test.setActive(true);
        test.setCreatedAt(LocalDateTime.now());
        test.setCreatedBy(currentUser.getEmail());

        return mapToResponse(labTestRepository.save(test));
    }

    public LabTestResponse getLabTestById(Long id) {
        LabTest test = labTestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lab test not found with id " + id));
        return mapToResponse(test);
    }

    public List<LabTestResponse> getAllActiveLabTests() {
        return labTestRepository.findAll()
                .stream()
                .filter(LabTest::getActive)
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional
    public LabTestResponse updateLabTest(Long id, LabTestRequest request) {
        User currentUser = userService.getCurrentUser();
        LabTest test = labTestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lab test not found with id " + id));

        test.setTestName(request.getTestName());
        test.setTestType(request.getTestType());
        test.setPrice(request.getPrice());
        test.setDepartmentId(request.getDepartmentId());
        test.setUpdatedAt(LocalDateTime.now());
        test.setUpdatedBy(currentUser.getEmail());

        return mapToResponse(labTestRepository.save(test));
    }

    @Transactional
    public void deactivateLabTest(Long id) {
        User currentUser = userService.getCurrentUser();
        LabTest test = labTestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lab test not found with id " + id));

        test.setActive(false);
        test.setUpdatedAt(LocalDateTime.now());
        test.setUpdatedBy(currentUser.getEmail());

        labTestRepository.save(test);
    }

    private LabTestResponse mapToResponse(LabTest test) {
        return LabTestResponse.builder()
                .labTestId(test.getId())
                .testCode(test.getTestCode())
                .testName(test.getTestName())
                .testType(test.getTestType())
                .price(test.getPrice())
                .active(test.getActive())
                .departmentId(test.getDepartmentId())
                .build();
    }
}

