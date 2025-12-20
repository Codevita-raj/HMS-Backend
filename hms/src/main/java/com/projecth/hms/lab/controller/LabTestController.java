package com.projecth.hms.lab.controller;

import com.projecth.hms.lab.dto.labTest.LabTestRequest;
import com.projecth.hms.lab.dto.labTest.LabTestResponse;
import com.projecth.hms.lab.service.LabTestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class LabTestController {

    private final LabTestService labTestService;

    @PostMapping("/lab/tests")
    public ResponseEntity<LabTestResponse> create(@RequestBody LabTestRequest request) {
        return ResponseEntity.ok(labTestService.createLabTest(request));
    }

    @GetMapping("/lab/tests/{id}")
    public ResponseEntity<LabTestResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(labTestService.getLabTestById(id));
    }

    @GetMapping("/lab/tests")
    public ResponseEntity<List<LabTestResponse>> getAll() {
        return ResponseEntity.ok(labTestService.getAllActiveLabTests());
    }

    @PutMapping("/lab/tests/{id}")
    public ResponseEntity<LabTestResponse> update(
            @PathVariable Long id,
            @RequestBody LabTestRequest request) {
        return ResponseEntity.ok(labTestService.updateLabTest(id, request));
    }

    @DeleteMapping("/lab/tests/{id}")
    public ResponseEntity<Void> deactivate(@PathVariable Long id) {
        labTestService.deactivateLabTest(id);
        return ResponseEntity.ok().build();
    }
}

