package com.example.indiapaymenthub.model;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;
    private String assignedById;
    
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
    public void setGatewayPaymentId(String paymentId) {
        this.gatewayPaymentId = paymentId;
    }
    public void setGatewaySignature(String signature) {
        this.gatewaySignature = signature;
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
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public void setAssignedById(String assignedById) {
        this.assignedById = assignedById;
    }
    public String getGatewayOrderId() { return gatewayOrderId; }
    // Getters and Setters
}
