package com.example.swd_cashier.apis;

import com.example.swd_cashier.models.Event;
import com.example.swd_cashier.models.ProductResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface EventAPIService {

    @GET("events/current-event")
    Call<Event> getCurrentEvent(@Query("brand-id") int brandId);
}
