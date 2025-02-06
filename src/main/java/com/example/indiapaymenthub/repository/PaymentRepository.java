package com.example.indiapaymenthub.repository;

import com.example.indiapaymenthub.model.*;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByUserId(String userId);
    Payment findByGatewayOrderId(String gatewayOrderId);
}
