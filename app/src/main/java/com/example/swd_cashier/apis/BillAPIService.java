package com.example.swd_cashier.apis;

import com.example.swd_cashier.models.BillRequest;
import com.example.swd_cashier.models.ProductResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface BillAPIService {

    @POST("bills")
    Call<ProductResponse> postBillAPI(@Body BillRequest billRequest, @Query("store-id") int storeId, @Query("cashier-id") int cashierId);
}
