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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.example.swd_cashier.R;
import com.example.swd_cashier.adapters.ProductListAdapter;
import com.example.swd_cashier.models.Product;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class MainFragment extends Fragment implements ProductListAdapter.ProductListAdapterOnClickHandler {

    private MainViewModel mViewModel;
    private ProductListAdapter productListAdapter;
    ArrayList<Product> modelRecyclerArrayList;
    private RecyclerView recyclerView;
    public static String keyWord;
    public static Dialog progressDialog;
    private NavController navController;

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

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            keyWord = bundle.getString("id");
        }

        recyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        recyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.HORIZONTAL));

        productListAdapter = new ProductListAdapter(getContext(), this);

        mViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        if (isNetworkConnected()) {
            mViewModel.loadProducts().observe(this, new Observer<ArrayList<Product>>() {
                @Override
                public void onChanged(ArrayList<Product> products) {
                    if (products != null) {
                        modelRecyclerArrayList = products;
                        productListAdapter.submitList(modelRecyclerArrayList);
                        progressDialog.dismiss();
                    }
                    //adapter.notifyDataSetChanged();

                }
            });
        }
        recyclerView.setAdapter(productListAdapter);
        productListAdapter.notifyDataSetChanged();
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
            Snackbar.make(requireView(), "Số lượng không đủ.", Snackbar.LENGTH_LONG)
                    .show();
            return;
        }
        boolean isAdded = mViewModel.addItemToCart(product);
        if (isAdded) {
            Snackbar.make(requireView(), product.getName() + " đã được thêm.", Snackbar.LENGTH_LONG)
                    .setAction("Thanh toán", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            navController.navigate(R.id.action_mainFragment_to_cartFragment);
                        }
                    })
                    .show();
        } else {
            Snackbar.make(requireView(), "Số lượng đã tới giới hạn.", Snackbar.LENGTH_LONG)
                    .show();
        }
    }
}