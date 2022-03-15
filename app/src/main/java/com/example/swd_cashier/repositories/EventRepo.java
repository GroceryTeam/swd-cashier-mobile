package com.example.swd_cashier.repositories;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.swd_cashier.EventFragment;
import com.example.swd_cashier.apis.ApiClient;
import com.example.swd_cashier.models.CartItem;
import com.example.swd_cashier.models.Event;
import com.example.swd_cashier.models.Product;
import com.example.swd_cashier.models.ProductResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventRepo {
    private final MutableLiveData<Event> mutableLiveEvent = new MutableLiveData<>();
    private Event event = new Event();
    EventFragment fragment = new EventFragment();

    public LiveData<Event> getEvent() {
        if (mutableLiveEvent.getValue() == null) {
            Toast.makeText(fragment.getActivity(), "Dữ liệu không tồn tại", Toast.LENGTH_SHORT).show();
        }
        return mutableLiveEvent;
    }

    public MutableLiveData<Event> callGetCurrentEventAPI(){
        Call<Event> call = ApiClient.getInstance().getEventApiService().getCurrentEvent(1);
        call.enqueue(new Callback<Event>() {

            @Override
            public void onResponse(Call<Event> call, Response<Event> response) {

                if(response.code() == 200) {

                    assert response.body() != null;

                    event = response.body();
                    mutableLiveEvent.setValue(event);
                }

            }

            @Override
            public void onFailure(Call<Event> call, Throwable t) {
                mutableLiveEvent.postValue(null);
                System.out.println("t.getMessage() = " + t.getMessage());

            }
        });
        return mutableLiveEvent;

    }
}
