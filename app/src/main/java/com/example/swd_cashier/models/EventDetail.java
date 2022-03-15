package com.example.swd_cashier.models;

import lombok.Data;

@Data
public class EventDetail {
    private int eventId;
    private int productId;
    private String productName;
    private int newPrice;
    private int originalPrice;
}
