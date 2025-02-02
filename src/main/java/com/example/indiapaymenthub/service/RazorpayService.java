
package com.example.indiapaymenthub.service;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class RazorpayService {

    private RazorpayClient razorpayClient;

    public RazorpayService() throws RazorpayException {
        this.razorpayClient = new RazorpayClient("rzp_test_35FAxQL6jAcLvD", "KxdviblSttixa3eVOXnMyCzm");
    }

    public Order createRazorpayOrder(Double amount, String receipt) throws RazorpayException {
        try {
            JSONObject orderRequest = new JSONObject();
            orderRequest.put("amount", (int) (amount * 100)); // Convert to paise
            orderRequest.put("currency", "INR");
            orderRequest.put("receipt", receipt);
            orderRequest.put("payment_capture", 1); // Auto-capture

            return razorpayClient.orders.create(orderRequest);
        } catch (RazorpayException e) {
            System.err.println("Error creating Razorpay order: " + e.getMessage());
            throw e;
        }
    }
}
