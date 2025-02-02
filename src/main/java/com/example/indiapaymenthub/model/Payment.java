package com.example.indiapaymenthub.model;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long user_id;
    private Long assigned_by;
    private Double amount;
    private String status; // PENDING, COMPLETED, FAILED
    private String gateway; // RAZORPAY, PAYTM, etc.
    private String transactionId;
    private String gatewayOrderId; // Order ID from the payment gateway
    private String gatewayPaymentId; // Payment ID after transaction
    private String gatewaySignature; // For payment verification (optional)

    public void setStatus(String status) {
        this.status = status;
    }
    public String getStatus() {
        return status;
    }
    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }
    public void setGateway(String gateway) {
        this.gateway = gateway;
    }
    public void setGatewayOrderId(String gatewayOrderId) {
        this.gatewayOrderId = gatewayOrderId;
    }
    public void setUserId(Long user_id) {
        this.user_id = user_id;
    }
    public void setAssignedById(Long assigned_by) {
        this.assigned_by = assigned_by;
    }
    // Getters and Setters
}
