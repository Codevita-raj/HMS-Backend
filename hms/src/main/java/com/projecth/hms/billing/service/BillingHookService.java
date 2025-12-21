package com.projecth.hms.billing.service;

import com.projecth.hms.billing.entity.Billing;
import com.projecth.hms.billing.entity.BillingItem;
import com.projecth.hms.inventory.entity.InventoryItem;
import com.projecth.hms.inventory.entity.PatientMedicineIssue;
import com.projecth.hms.inventory.repository.InventoryItemRepository;
import com.projecth.hms.inventory.repository.PatientMedicineIssueRepository;
import com.projecth.hms.lab.entity.LabOrder;
import com.projecth.hms.lab.entity.LabOrderItem;
import com.projecth.hms.lab.entity.LabTest;
import com.projecth.hms.lab.repository.LabOrderItemRepository;
import com.projecth.hms.lab.repository.LabOrderRepository;
import com.projecth.hms.lab.repository.LabTestRepository;
import com.projecth.hms.shared.enums.BillingItemType;
import com.projecth.hms.wardmanagement.entity.Bed;
import com.projecth.hms.wardmanagement.entity.BedOccupancy;
import com.projecth.hms.wardmanagement.repository.BedOccupancyRepository;
import com.projecth.hms.wardmanagement.repository.BedRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BillingHookService {

//    public void onDischarge(Long admissionId) {}
//
//    public void onMedicineIssued(Long admissionId) {}
//    public void onMedicineReturned(Long admissionId) {}
//    public void onLabOrdered(Long admissionId) {}

}

