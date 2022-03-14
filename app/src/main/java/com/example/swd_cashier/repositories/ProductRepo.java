package com.example.swd_cashier.repositories;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.swd_cashier.apis.ApiClient;
import com.example.swd_cashier.models.Product;
import com.example.swd_cashier.models.ProductResponse;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductRepo {
    private final MutableLiveData<ArrayList<Product>> mutableLiveProductList = new MutableLiveData<>();
    private ArrayList<Product> productList = new ArrayList<>();


    public MutableLiveData<ArrayList<Product>> callGetProductsAPI(String searchTerm){
        Call<ProductResponse> call = ApiClient.getInstance().getProductAPIService().getProductList(1, searchTerm == null ? "" : searchTerm, 1, 100);
        call.enqueue(new Callback<ProductResponse>() {

            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {

                if(response.code() == 200) {

                    assert response.body() != null;

                    productList = response.body().getData();
                    System.out.println("Product size: "  + productList.size());
                    Log.e("Product size: "  + productList.size(), "product product");
                    mutableLiveProductList.setValue(productList);
                }

            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                //failed
                mutableLiveProductList.postValue(null);
                System.out.println("t.getMessage() = " + t.getMessage());

            }
        });
        return mutableLiveProductList;

    }
}
