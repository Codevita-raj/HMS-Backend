package com.projecth.hms.billing.service;

import com.projecth.hms.billing.dto.BillingItemDTO;
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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BillingAggregationService {

    private final PatientMedicineIssueRepository patientMedicineIssueRepository;
    private final InventoryItemRepository inventoryItemRepository;
    private final LabOrderItemRepository labOrderItemRepository;
    private final LabTestRepository labTestRepository;
    private final LabOrderRepository labOrderRepository;
    private final BedOccupancyRepository bedOccupancyRepository;
    private final BedRepository bedRepository;

    public List<BillingItemDTO> aggregateCharges(Long admissionId) {
        List<BillingItemDTO> billingItems = new ArrayList<>();
        billingItems.addAll(aggregateBedCharges(admissionId));
        billingItems.addAll(aggregateLabCharges(admissionId));
        billingItems.addAll(aggregateMedicineCharges(admissionId));
        return billingItems;
    }

    private List<BillingItemDTO> aggregateBedCharges(Long admissionId) {
        List<BedOccupancy> occupancies = bedOccupancyRepository.findByAdmissionId(admissionId);
        List<BillingItemDTO> items = new ArrayList<>();

        for (BedOccupancy occupancy : occupancies) {
            Bed bed = bedRepository.findById(occupancy.getBedId())
                    .orElseThrow(() -> new RuntimeException("Bed not found: " + occupancy.getBedId()));

            LocalDate from = occupancy.getOccupiedFrom().toLocalDate();
            LocalDate till = occupancy.getOccupiedTill() != null
                    ? occupancy.getOccupiedTill().toLocalDate()
                    : LocalDate.now();

            long days = ChronoUnit.DAYS.between(from, till);
            if (days == 0) days = 1;

            BillingItemDTO dto = new BillingItemDTO();
            dto.setItemType(BillingItemType.BED);
            dto.setDescription("Bed Charges: " + bed.getBedNumber());
            dto.setQuantity((int) days);
            dto.setUnitPrice(bed.getDailyCharge());
            dto.setAmount(bed.getDailyCharge().multiply(BigDecimal.valueOf(days)));

            items.add(dto);
        }
        return items;
    }

    private List<BillingItemDTO> aggregateMedicineCharges(Long admissionId) {
        List<PatientMedicineIssue> issues = patientMedicineIssueRepository.findByAdmissionId(admissionId);

        return issues.stream().map(issue -> {
            InventoryItem inventoryItem = inventoryItemRepository.findById(issue.getItemId())
                    .orElseThrow(() -> new RuntimeException("Inventory item not found for id " + issue.getItemId()));

            BillingItemDTO dto = new BillingItemDTO();
            dto.setItemType(BillingItemType.MEDICINE);
            dto.setReferenceId(issue.getId());
            dto.setDescription(inventoryItem.getName());
            dto.setQuantity(issue.getQuantity());
            dto.setUnitPrice(inventoryItem.getUnitPrice());
            dto.setAmount(inventoryItem.getUnitPrice().multiply(BigDecimal.valueOf(issue.getQuantity())));

            return dto;
        }).toList();
    }

    public List<BillingItemDTO> aggregateLabCharges(Long admissionId) {

        List<LabOrder> labOrders =
                labOrderRepository.findByAdmissionId(admissionId);

        if (labOrders.isEmpty()) {
            return List.of();
        }

        List<BillingItemDTO> items = new ArrayList<>();

        for (LabOrder order : labOrders) {

            List<LabOrderItem> orderItems =
                    labOrderItemRepository.findByLabOrderId(order.getId());

            for (LabOrderItem item : orderItems) {

                LabTest test = labTestRepository.findById(item.getLabTestId())
                        .orElseThrow(() -> new RuntimeException("LabTest not found"));

                BillingItemDTO dto = new BillingItemDTO();
                dto.setItemType(BillingItemType.LAB);
                dto.setReferenceId(item.getId());
                dto.setDescription(test.getTestName());
                dto.setQuantity(1);
                dto.setUnitPrice(test.getPrice());
                dto.setAmount(test.getPrice());

                items.add(dto);
            }
        }

        return items;
    }

}
