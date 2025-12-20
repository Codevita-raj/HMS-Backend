package com.projecth.hms.inventory.service;

import com.projecth.hms.inventory.entity.InventoryStock;
import com.projecth.hms.inventory.repository.InventoryStockRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class InventoryStockService {

    private final InventoryStockRepository stockRepository;

    @Transactional
    public void increaseStock(Long itemId, Long locationId, int quantity) {

        InventoryStock stock = stockRepository
                .findByItemIdAndLocationId(itemId, locationId)
                .orElseGet(() -> createNewStock(itemId, locationId));

        stock.setAvailableQuantity(stock.getAvailableQuantity() + quantity);
        stock.setLastUpdatedAt(LocalDateTime.now());

        stockRepository.save(stock);
    }

    @Transactional
    public void decreaseStock(Long itemId, Long locationId, int quantity) {

        InventoryStock stock = stockRepository
                .findByItemIdAndLocationId(itemId, locationId)
                .orElseThrow(() -> new RuntimeException("Stock not found"));

        if (stock.getAvailableQuantity() < quantity) {
            throw new RuntimeException("Insufficient stock");
        }

        stock.setAvailableQuantity(stock.getAvailableQuantity() - quantity);
        stock.setLastUpdatedAt(LocalDateTime.now());

        stockRepository.save(stock);
    }

    private InventoryStock createNewStock(Long itemId, Long locationId) {
        InventoryStock stock = new InventoryStock();
        stock.setItemId(itemId);
        stock.setLocationId(locationId);
        stock.setAvailableQuantity(0);
        stock.setReservedQuantity(0);
        stock.setLastUpdatedAt(LocalDateTime.now());
        return stock;
    }
}

