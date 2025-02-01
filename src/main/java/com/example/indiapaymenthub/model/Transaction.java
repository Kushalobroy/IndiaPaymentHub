package com.example.indiapaymenthub.model;


import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "payment_id")
    private Payment payment;

    private LocalDateTime transactionDate;
    private Double amount;

    @Enumerated(EnumType.STRING)
    private Status status; // SUCCESS, FAILED

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod; // CARD, UPI, NETBANKING, WALLET

    private String referenceId;

    public enum Status {
        SUCCESS, FAILED
    }

    public enum PaymentMethod {
        CARD, UPI, NETBANKING, WALLET
    }

    // Getters and Setters
    public void setPayment(Payment payment) {
        this.payment = payment;
    }
    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }
    public void setAmount(Double amount) {
        this.amount = amount;
    }
    public void setStatus(Status status) {
        this.status = status;
    }
    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }
    public Payment getPayment() {
        return payment;
    }
    
}