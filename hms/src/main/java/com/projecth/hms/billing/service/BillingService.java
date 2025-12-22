package com.projecth.hms.billing.service;

import com.projecth.hms.billing.dto.BillingItemDTO;
import com.projecth.hms.billing.entity.Billing;
import com.projecth.hms.billing.entity.BillingItem;
import com.projecth.hms.billing.repository.BillingItemRepository;
import com.projecth.hms.billing.repository.BillingRepository;
import com.projecth.hms.shared.enums.BillStatus;
import com.projecth.hms.user.entity.User;
import com.projecth.hms.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BillingService {

    private final BillingRepository billingRepository;
    private final BillingItemRepository billingItemRepository;
    private final BillingAggregationService billingAggregationService;
    private final UserService userService;

    @Transactional
    public Billing generateBilling(Long admissionId, Long patientId) {
         User currentUser = userService.getCurrentUser();
        List<BillingItemDTO> aggregatedItems = billingAggregationService.aggregateCharges(admissionId);

        if (aggregatedItems.isEmpty()) {
            throw new RuntimeException("No charges found for admission " + admissionId);
        }

        //  Calculate totals
        BigDecimal totalAmount = aggregatedItems.stream()
                .map(BillingItemDTO::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal discountAmount = BigDecimal.ZERO;
        BigDecimal taxAmount = BigDecimal.ZERO;
        BigDecimal netPayable = totalAmount.subtract(discountAmount).add(taxAmount);

        //  Create Billing entity
        Billing billing = new Billing();
        billing.setAdmissionId(admissionId);
        billing.setPatientId(patientId);
        billing.setTotalAmount(totalAmount);
        billing.setDiscountAmount(discountAmount);
        billing.setTaxAmount(taxAmount);
        billing.setNetPayable(netPayable);
        billing.setStatus(BillStatus.DRAFT);
        billing.setGeneratedAt(LocalDateTime.now());
        billing.setGeneratedBy(currentUser.getEmail());

        Billing savedBilling = billingRepository.save(billing);

        //  Map BillingItemDTO → BillingItem and persist
        List<BillingItem> billingItems = aggregatedItems.stream().map(dto -> {
            BillingItem item = new BillingItem();
            item.setBillingId(savedBilling.getId());
            item.setItemType(dto.getItemType());
            item.setReferenceId(dto.getReferenceId());
            item.setDescription(dto.getDescription());
            item.setQuantity(dto.getQuantity());
            item.setUnitPrice(dto.getUnitPrice());
            item.setAmount(dto.getAmount());
            return item;
        }).collect(Collectors.toList());

        billingItemRepository.saveAll(billingItems);

        return savedBilling;
    }

    public List<Billing> getAllBills() {
        return billingRepository.findAll();
    }

    public Billing getBillingById(Long billingId) {
        return billingRepository.findById(billingId)
                .orElseThrow(() -> new RuntimeException("Billing not found for id " + billingId));
    }

    @Transactional
    public void markBillAsPaid(Long billingId) {
        Billing billing = getBillingById(billingId);
        billing.setStatus(BillStatus.PAID);
        billingRepository.save(billing);
    }
}
