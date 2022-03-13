package com.example.swd_cashier.adapters;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.swd_cashier.R;
import com.example.swd_cashier.models.Product;

public class ProductListAdapter extends ListAdapter<Product, ProductListAdapter.ProductViewHolder> {

    private final Context mContext;

    private final ProductListAdapterOnClickHandler clickHandler;

    public interface ProductListAdapterOnClickHandler {
        void onClick(Product id);
    }

    public ProductListAdapter(Context context, ProductListAdapterOnClickHandler clickHandler) {
        super(DIFF_CALLBACK);
        this.mContext = context;
        this.clickHandler = clickHandler;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_row, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        Product item = getItem(position);

        if (item != null) {
            holder.productName.setText(item.getName());

            holder.quantity.setText(String.valueOf(item.getQuantity()));

            holder.price.setText(String.valueOf(item.getEventPrice()));
        } else {
            Toast.makeText(mContext, "Item is null", Toast.LENGTH_LONG).show();
        }
    }
    public static final DiffUtil.ItemCallback<Product> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Product>() {
                @Override
                public boolean areItemsTheSame(
                        @NonNull Product oldUser, @NonNull Product newUser) {
                    // User properties may have changed if reloaded from the DB, but ID is fixed
                    return oldUser.getId() == newUser.getId();
                }

                @Override
                public boolean areContentsTheSame(
                        @NonNull Product oldUser, @NonNull Product newUser) {
                    // NOTE: if you use equals, your object must properly override Object#equals()
                    // Incorrectly returning false here will result in too many animations.
                    return oldUser.equals(newUser);
                }
            };



    class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        // Create view instances
        TextView productName;
        TextView quantity;
        TextView price;

        private ProductViewHolder(View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.tvProductName);
            quantity = itemView.findViewById(R.id.tvQuantity);
            price = itemView.findViewById(R.id.tvPrice);
            // Register a callback to be invoked when this view is clicked.
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            // Get the position of the view in the adapter
            int mItemSelected = getAdapterPosition();
            // Get the id of the video
            Product product = getCurrentList().get(mItemSelected);
            if(product == null){
                return;
            }
            // Send product through click
            clickHandler.onClick(product);
        }
    }
}
