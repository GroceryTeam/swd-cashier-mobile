package com.example.swd_cashier.ui.main;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.swd_cashier.R;
import com.example.swd_cashier.adapters.ProductListAdapter;
import com.example.swd_cashier.models.Event;
import com.example.swd_cashier.models.Product;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainFragment extends Fragment implements ProductListAdapter.ProductListAdapterOnClickHandler {

    private MainViewModel mViewModel;
    private ProductListAdapter productListAdapter;
    ArrayList<Product> modelRecyclerArrayList;
    private RecyclerView recyclerView;
    public static String keyWord;
    public static Dialog progressDialog;
    private NavController navController;
    EditText edtSearchProduct;
    TextView tvCurrentEventName;
    Button btnViewCurrentEvent;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
        recyclerView = view.findViewById(R.id.productRecyclerView);
        progressDialog = createProgressDialog(getContext());
        tvCurrentEventName = view.findViewById(R.id.tvEvent);
        btnViewCurrentEvent = view.findViewById(R.id.btnViewEventDetail);

        recyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        recyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.HORIZONTAL));

        productListAdapter = new ProductListAdapter(getContext(), this);

        mViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        if (isNetworkConnected()) {
            mViewModel.loadProducts(null).observe(this, new Observer<ArrayList<Product>>() {
                @Override
                public void onChanged(ArrayList<Product> products) {
                    if (products != null) {
                        modelRecyclerArrayList = products;
                        productListAdapter.submitList(modelRecyclerArrayList);
                        progressDialog.dismiss();
                    }
                }
            });

            mViewModel.loadCurrentEvent().observe(this, new Observer<Event>() {
                @Override
                public void onChanged(Event event) {
                    if (event != null) {
                        tvCurrentEventName.setText(event.getEventName());
                    } else {
                        tvCurrentEventName.setText("Kh??ng c?? s??? ki???n n??o ??ang di???n ra");
                    }

                }
            });
        }
        recyclerView.setAdapter(productListAdapter);
        productListAdapter.notifyDataSetChanged();

        btnViewCurrentEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_mainFragment_to_eventFragment);
            }
        });

        edtSearchProduct = view.findViewById(R.id.edtSearchProduct);
        edtSearchProduct.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            final Handler handler = new Handler();
            Runnable workRunnable;
            @Override
            public void afterTextChanged(final Editable s) {
                handler.removeCallbacks(workRunnable);
                workRunnable = () -> reloadProduct(s.toString());
                handler.postDelayed(workRunnable, 300 /*delay*/);
            }

            private final void reloadProduct(String searchTerm) {
                progressDialog = createProgressDialog(getContext());
                mViewModel.loadProducts(searchTerm).observe(MainFragment.this, new Observer<ArrayList<Product>>() {
                    @Override
                    public void onChanged(ArrayList<Product> products) {
                        if (products != null) {
                            modelRecyclerArrayList = products;
                            productListAdapter.submitList(modelRecyclerArrayList);
                            progressDialog.dismiss();
                        }
                    }
                });
                recyclerView.setAdapter(productListAdapter);
                productListAdapter.notifyDataSetChanged();
            }
        });


    }


    private boolean isNetworkConnected() {
        ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        } else {
            progressDialog.dismiss();
            return false;
        }
    }


    public static Dialog createProgressDialog(Context context) {
        Dialog progressDialog = new Dialog(context);
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog.setCancelable(false);
        progressDialog.setContentView(R.layout.dialog_layout);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.show();
        return progressDialog;
    }

    @Override
    public void onClick(Product product) {
        if (product.getQuantity() == 0) {
            Snackbar.make(requireView(), "S??? l?????ng kh??ng ?????.", Snackbar.LENGTH_LONG)
                    .show();
            return;
        }
        boolean isAdded = mViewModel.addItemToCart(product);
        if (isAdded) {
            Snackbar.make(requireView(), product.getName() + " ???? ???????c th??m.", Snackbar.LENGTH_LONG)
                    .setAction("Thanh to??n", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            navController.navigate(R.id.action_mainFragment_to_cartFragment);
                        }
                    })
                    .show();
        } else {
            Snackbar.make(requireView(), "S??? l?????ng ???? t???i gi???i h???n.", Snackbar.LENGTH_LONG)
                    .show();
        }
    }
}