package com.projecth.hms.inventory.controller;

import com.projecth.hms.inventory.dto.PatientMedicineIssueRequest;
import com.projecth.hms.inventory.service.PatientMedicineIssueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PatientMedicineIssueController {

    private final PatientMedicineIssueService issueService;

    @PostMapping("/patient/medicine/issue")
    public ResponseEntity<String> issueMedicine(
            @RequestBody PatientMedicineIssueRequest request) {

        issueService.issueMedicine(request);
        return ResponseEntity.ok("Medicine issued successfully");
    }
}

