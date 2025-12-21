package com.projecth.hms.billing.repository;


import com.projecth.hms.billing.entity.Billing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillingRepository extends JpaRepository<Billing, Long> {
    // You can add custom queries if needed later
}

