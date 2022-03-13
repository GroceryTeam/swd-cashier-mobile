package com.example.swd_cashier.models;

import java.util.List;

public class BillRequest {
    private int totalPrice;
    private List<BillDetail> details;

    public BillRequest(int totalPrice, List<BillDetail> details) {
        this.totalPrice = totalPrice;
        this.details = details;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<BillDetail> getDetails() {
        return details;
    }

    public void setDetails(List<BillDetail> details) {
        this.details = details;
    }
}
