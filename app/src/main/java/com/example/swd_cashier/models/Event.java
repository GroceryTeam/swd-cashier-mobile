package com.example.swd_cashier.models;

import java.util.List;

import lombok.Data;

@Data
public class Event {
    private int id;
    private String eventName;
    private int status;
    private int productCount;
    private List<EventDetail> eventDetails;
}
