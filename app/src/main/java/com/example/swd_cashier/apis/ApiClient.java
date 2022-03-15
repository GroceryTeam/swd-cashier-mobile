package com.example.swd_cashier.apis;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ApiClient {
    private static final String BASE_URL = "https://grocerycloudcashierapi2.azurewebsites.net/api/v1.0/";
    private static ApiClient instance;
    private Retrofit retrofit; //retrofit object

    private ApiClient() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
        retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson)).client(client).build();

    }

    public static synchronized ApiClient getInstance() {
        if (instance == null) {
            instance = new ApiClient();
        }
        return instance;

    }

    public ProductAPIService getProductAPIService() {
        return retrofit.create(ProductAPIService.class);
    }

    public BillAPIService getBillApiService() {
        return retrofit.create(BillAPIService.class);
    }

    public EventAPIService getEventApiService() {
        return retrofit.create(EventAPIService.class);
    }
}
