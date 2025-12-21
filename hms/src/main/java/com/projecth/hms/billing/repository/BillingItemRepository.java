package com.projecth.hms.billing.repository;

import com.projecth.hms.billing.entity.BillingItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillingItemRepository extends JpaRepository<BillingItem, Long> {
    List<BillingItem> findByBillingId(Long billingId);
}

