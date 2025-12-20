package com.projecth.hms.inventory.service;

import com.projecth.hms.inventory.dto.InventoryTransactionRequest;
import com.projecth.hms.inventory.entity.InventoryTransaction;
import com.projecth.hms.inventory.repository.InventoryTransactionRepository;
import com.projecth.hms.shared.enums.InventoryTransactionType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class InventoryTransactionService {

    private final InventoryTransactionRepository transactionRepository;
    private final InventoryStockService stockService;

    @Transactional
    public void createTransaction(InventoryTransactionRequest request) {

        //  Persist transaction first (audit-safe)
        InventoryTransaction tx = new InventoryTransaction();
        tx.setItemId(request.getItemId());
        tx.setLocationId(request.getLocationId());
        tx.setTransactionType(request.getTransactionType());
        tx.setQuantity(request.getQuantity());
        tx.setPatientId(request.getPatientId());
        tx.setAdmissionId(request.getAdmissionId());
        tx.setReferenceId(request.getReferenceId());
        tx.setRemarks(request.getRemarks());
        tx.setTransactionAt(LocalDateTime.now());
        tx.setCreatedBy("SYSTEM");

        transactionRepository.save(tx);

        //  Update stock based on transaction type
        applyStockMovement(request);
    }

    private void applyStockMovement(InventoryTransactionRequest request) {

        InventoryTransactionType type = request.getTransactionType();

        switch (type) {

            case IN, RETURN ->
                    stockService.increaseStock(
                            request.getItemId(),
                            request.getLocationId(),
                            request.getQuantity()
                    );

            case OUT, EXPIRED ->
                    stockService.decreaseStock(
                            request.getItemId(),
                            request.getLocationId(),
                            request.getQuantity()
                    );

            case ADJUSTMENT -> {
                if (request.getQuantity() > 0) {
                    stockService.increaseStock(
                            request.getItemId(),
                            request.getLocationId(),
                            request.getQuantity()
                    );
                } else {
                    stockService.decreaseStock(
                            request.getItemId(),
                            request.getLocationId(),
                            Math.abs(request.getQuantity())
                    );
                }
            }
        }
    }
}

