package com.example.swd_cashier.adapters;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.swd_cashier.R;
import com.example.swd_cashier.models.CartItem;
import com.example.swd_cashier.models.Product;
import com.example.swd_cashier.databinding.CartRowBinding;

public class CartListAdapter extends ListAdapter<CartItem, CartListAdapter.CartViewHolder> {


    private final CartInterface cartInterface;

    public interface CartInterface {
        void changeQuantity(CartItem item, int quantity);
        void deleteItem(CartItem item);
    }

    public CartListAdapter(CartInterface cartInterface) {
        super(DIFF_CALLBACK);
        this.cartInterface = cartInterface;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        CartRowBinding cartRowBinding = CartRowBinding.inflate(layoutInflater, parent, false);
        return new CartViewHolder(cartRowBinding);
    }

    @Override
    public void onBindViewHolder(CartViewHolder holder, int position) {
        holder.cartRowBinding.setCartItem(getItem(position));
        holder.cartRowBinding.executePendingBindings();
    }
    public static final DiffUtil.ItemCallback<CartItem> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<CartItem>() {
                @Override
                public boolean areItemsTheSame(
                        @NonNull CartItem oldUser, @NonNull CartItem newUser) {
                    // User properties may have changed if reloaded from the DB, but ID is fixed
                    return oldUser.getProduct().getId() == newUser.getProduct().getId();
                }

                @Override
                public boolean areContentsTheSame(
                        @NonNull CartItem oldUser, @NonNull CartItem newUser) {
                    // NOTE: if you use equals, your object must properly override Object#equals()
                    // Incorrectly returning false here will result in too many animations.
                    return oldUser.getProduct().equals(newUser.getProduct());
                }
            };



    class CartViewHolder extends RecyclerView.ViewHolder {

        CartRowBinding cartRowBinding;
        public CartViewHolder(@NonNull CartRowBinding cartRowBinding) {
            super(cartRowBinding.getRoot());
            this.cartRowBinding = cartRowBinding;

            cartRowBinding.deleteProductButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cartInterface.deleteItem(getItem(getAdapterPosition()));
                }
            });

            cartRowBinding.edtCartQuantity.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if(charSequence.length() != 0)
                        cartInterface.changeQuantity(getItem(getAdapterPosition()), Integer.parseInt(charSequence.toString()));
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        }
    }
}
