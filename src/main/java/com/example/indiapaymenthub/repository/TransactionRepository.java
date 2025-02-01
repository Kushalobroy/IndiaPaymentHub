package com.example.indiapaymenthub.repository;

import com.example.indiapaymenthub.model.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
