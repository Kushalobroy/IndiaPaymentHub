package com.example.indiapaymenthub.repository;

import com.example.indiapaymenthub.model.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByUserId(String userId);
    Payment findByGatewayOrderId(String gatewayOrderId);
    @Query("SELECT COALESCE(SUM(p.amount), 0) FROM Payment p WHERE p.status = :status")
    long sumAmountByStatus(@Param("status") String status);
}
