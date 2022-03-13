package com.example.swd_cashier.apis;

import com.example.swd_cashier.models.ProductResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ProductAPIService {

    @GET("products")
    Call<ProductResponse> getProductList(@Query("store-id") int storeId,
                                         @Query("search-term") String searchTerm,
//                                         @Query("category-id") Integer categoryId,
                                         @Query("page-index") int pageIndex,
                                         @Query("page-size") int pageSize);
}
