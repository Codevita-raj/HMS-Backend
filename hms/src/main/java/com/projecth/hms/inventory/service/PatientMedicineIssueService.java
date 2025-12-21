package com.projecth.hms.inventory.service;

import com.projecth.hms.inventory.dto.InventoryTransactionRequest;
import com.projecth.hms.inventory.dto.PatientMedicineIssueRequest;
import com.projecth.hms.inventory.entity.PatientMedicineIssue;
import com.projecth.hms.inventory.repository.PatientMedicineIssueRepository;
import com.projecth.hms.shared.enums.InventoryTransactionType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PatientMedicineIssueService {

    private final PatientMedicineIssueRepository issueRepository;
    private final InventoryTransactionService transactionService;

    @Transactional
    public void issueMedicine(PatientMedicineIssueRequest request) {

        //  Save Issue Record
        PatientMedicineIssue issue = new PatientMedicineIssue();
        issue.setPatientId(request.getPatientId());
        issue.setAdmissionId(request.getAdmissionId());
        issue.setItemId(request.getItemId());
        issue.setLocationId(request.getLocationId());
        issue.setQuantity(request.getQuantity());
        issue.setRemarks(request.getRemarks());
        issue.setIssuedAt(LocalDateTime.now());
        issue.setIssuedBy("NURSE");
        issue.setCreatedBy("SYSTEM");

        issueRepository.save(issue);

        //  Reduce Stock (Inventory Transaction)
        InventoryTransactionRequest tx = new InventoryTransactionRequest();
        tx.setItemId(request.getItemId());
        tx.setLocationId(request.getLocationId());
        tx.setTransactionType(InventoryTransactionType.OUT);
        tx.setQuantity(request.getQuantity());
        tx.setPatientId(request.getPatientId());
        tx.setAdmissionId(request.getAdmissionId());
        tx.setReferenceId(issue.getId());
        tx.setRemarks("Issued to patient");

        transactionService.createTransaction(tx);
    }
}

