package com.projecth.hms.inventory.service;

import com.projecth.hms.inventory.dto.InventoryTransactionRequest;
import com.projecth.hms.inventory.dto.PatientMedicineIssueRequest;
import com.projecth.hms.inventory.entity.PatientMedicineIssue;
import com.projecth.hms.inventory.repository.PatientMedicineIssueRepository;
import com.projecth.hms.shared.enums.InventoryTransactionType;
import com.projecth.hms.user.entity.User;
import com.projecth.hms.user.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PatientMedicineIssueService {

    private final PatientMedicineIssueRepository issueRepository;
    private final InventoryTransactionService transactionService;
    private final UserService userService;
    @Transactional
    public void issueMedicine(PatientMedicineIssueRequest request) {
        User currentUser = userService.getCurrentUser();
        //  Save Issue Record
        PatientMedicineIssue issue = new PatientMedicineIssue();
        issue.setPatientId(request.getPatientId());
        issue.setAdmissionId(request.getAdmissionId());
        issue.setItemId(request.getItemId());
        issue.setLocationId(request.getLocationId());
        issue.setQuantity(request.getQuantity());
        issue.setRemarks(request.getRemarks());
        issue.setIssuedAt(LocalDateTime.now());
        issue.setIssuedBy(currentUser.getEmail());
        issue.setCreatedBy(currentUser.getEmail());

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

