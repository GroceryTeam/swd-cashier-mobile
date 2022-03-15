package com.example.swd_cashier.adapters;

import android.content.Context;
import android.graphics.Paint;
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
import com.example.swd_cashier.models.EventDetail;


public class EventListAdapter extends ListAdapter<EventDetail, EventListAdapter.EventViewHolder> {

    private final Context mContext;

    public EventListAdapter(Context context) {
        super(DIFF_CALLBACK);
        this.mContext = context;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_row, parent, false);
        return new EventListAdapter.EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EventListAdapter.EventViewHolder holder, int position) {
        EventDetail item = getItem(position);

        if (item != null) {
            holder.productName.setText(item.getProductName());

            holder.originalPrice.setText(String.valueOf(item.getOriginalPrice()) + " VND");

            holder.eventPrice.setText(String.valueOf(item.getNewPrice()) + " VND");
        } else {
            Toast.makeText(mContext, "Item is null", Toast.LENGTH_LONG).show();
        }
    }
    public static final DiffUtil.ItemCallback<EventDetail> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<EventDetail>() {
                @Override
                public boolean areItemsTheSame(
                        @NonNull EventDetail oldUser, @NonNull EventDetail newUser) {
                    // User properties may have changed if reloaded from the DB, but ID is fixed
                    return oldUser.getProductId() == newUser.getProductId();
                }

                @Override
                public boolean areContentsTheSame(
                        @NonNull EventDetail oldUser, @NonNull EventDetail newUser) {
                    // NOTE: if you use equals, your object must properly override Object#equals()
                    // Incorrectly returning false here will result in too many animations.
                    return oldUser.equals(newUser);
                }
            };



    class EventViewHolder extends RecyclerView.ViewHolder {

        // Create view instances
        TextView productName;
        TextView originalPrice;
        TextView eventPrice;

        private EventViewHolder(View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.tvEventRowName);
            originalPrice = itemView.findViewById(R.id.tvEventRowOriginalPrice);
            originalPrice.setPaintFlags(originalPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            eventPrice = itemView.findViewById(R.id.tvEventRowEventPrice);
        }
    }
}