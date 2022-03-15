package com.example.swd_cashier;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.swd_cashier.adapters.EventListAdapter;
import com.example.swd_cashier.adapters.ProductListAdapter;
import com.example.swd_cashier.models.CartItem;
import com.example.swd_cashier.models.Event;
import com.example.swd_cashier.models.Product;
import com.example.swd_cashier.ui.main.MainViewModel;

import java.util.ArrayList;
import java.util.List;


public class EventFragment extends Fragment {

    private MainViewModel mViewModel;
    TextView tvCurrentEventName;
    private EventListAdapter eventListAdapter;
    private RecyclerView recyclerView;

    public EventFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_event, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvCurrentEventName = view.findViewById(R.id.tvCurrentEventName);
        recyclerView = view.findViewById(R.id.eventRecyclerView);

        recyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        recyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.HORIZONTAL));

        eventListAdapter = new EventListAdapter(getContext());

        mViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        mViewModel.getEvent().observe(getViewLifecycleOwner(), new Observer<Event>() {
            @Override
            public void onChanged(Event event) {
                tvCurrentEventName.setText(event.getEventName());
                eventListAdapter.submitList(event.getEventDetails());
            }
        });

        recyclerView.setAdapter(eventListAdapter);
        eventListAdapter.notifyDataSetChanged();
    }
}