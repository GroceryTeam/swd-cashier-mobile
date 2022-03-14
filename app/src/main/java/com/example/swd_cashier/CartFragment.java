package com.example.swd_cashier;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;
import androidx.lifecycle.Observer;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.swd_cashier.adapters.CartListAdapter;
import com.example.swd_cashier.adapters.ProductListAdapter;
import com.example.swd_cashier.databinding.FragmentCartBinding;
import com.example.swd_cashier.models.BillDetail;
import com.example.swd_cashier.models.BillRequest;
import com.example.swd_cashier.models.CartItem;
import com.example.swd_cashier.models.Product;
import com.example.swd_cashier.ui.main.MainViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CartFragment extends Fragment implements CartListAdapter.CartInterface {

    NavController navController;
    MainViewModel mainViewModel;
    FragmentCartBinding fragmentCartBinding;
    public static Dialog progressDialog;

    public CartFragment() {
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
        fragmentCartBinding = FragmentCartBinding.inflate(inflater, container, false);
        return fragmentCartBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        final CartListAdapter cartListAdapter = new CartListAdapter(this);

        fragmentCartBinding.cartRecyclerView.setAdapter(cartListAdapter);
        fragmentCartBinding.cartRecyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));


        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        mainViewModel.getCart().observe(getViewLifecycleOwner(), new Observer<List<CartItem>>() {
            @Override
            public void onChanged(List<CartItem> cartItems) {
                cartListAdapter.submitList(cartItems);
                fragmentCartBinding.checkoutBtn.setEnabled(cartItems.size() > 0);
            }
        });
//        fragmentCartBinding.cartRecyclerView.setAdapter(cartListAdapter);
//        cartListAdapter.notifyDataSetChanged();

        mainViewModel.getTotalPrice().observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                fragmentCartBinding.orderTotalTextView.setText("Tổng: " + aDouble.toString() + " VND");
            }
        });

        fragmentCartBinding.checkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<CartItem> currentCart = cartListAdapter.getCurrentList();
                List<BillDetail> details = new ArrayList<>();
                int totalPrice = 0;
                for (CartItem item: currentCart) {
                    int productId = item.getProduct().getId();
                    int quantity = item.getQuantity();
                    details.add(new BillDetail(productId, quantity));
                    totalPrice += quantity * item.getProduct().getEventPrice();
                }
                BillRequest billRequest = new BillRequest(totalPrice, details);

                progressDialog = createProgressDialog(getContext());
                mainViewModel.checkoutBill(billRequest);

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable(){
                    public void run(){
                        progressDialog.dismiss();
                        navController.navigate(R.id.action_cartFragment_to_orderFragment);
                    }
                }, 2000);

            }
        });


    }

    @Override
    public void deleteItem(CartItem cartItem) {
        mainViewModel.removeItemFromCart(cartItem);
    }

    @Override
    public void changeQuantity(CartItem cartItem, int quantity) {
        mainViewModel.changeQuantity(cartItem, quantity);
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
}