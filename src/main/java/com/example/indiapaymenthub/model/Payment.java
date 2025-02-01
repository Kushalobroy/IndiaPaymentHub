package com.example.indiapaymenthub.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "assigned_by")
    private User assignedBy;

    private Double amount;
    private String status; // PENDING, COMPLETED, FAILED
    private String gateway; // RAZORPAY, PAYTM, etc.
    private String transactionId;
    private String gatewayOrderId; // Order ID from the payment gateway
    private String gatewayPaymentId; // Payment ID after transaction
    private String gatewaySignature; // For payment verification (optional)


    @OneToMany(mappedBy = "payment", cascade = CascadeType.ALL)
    private List<Transaction> transactions;

    public void setStatus(String status) {
        this.status = status;
    }
    public String getStatus() {
        return status;
    }
    public double getAmount() {
        return amount;
    }
    // Getters and Setters
}
