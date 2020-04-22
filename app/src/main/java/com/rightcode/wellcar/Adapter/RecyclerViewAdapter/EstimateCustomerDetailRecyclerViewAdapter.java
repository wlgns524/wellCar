package com.rightcode.wellcar.Adapter.RecyclerViewAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rightcode.wellcar.Adapter.ViewHolder.CommonRecyclerViewHolder;
import com.rightcode.wellcar.Adapter.ViewHolder.EstimateCustomerDetailViewHolder;
import com.rightcode.wellcar.R;
import com.rightcode.wellcar.network.model.response.estimate.EstimateDetail;
import com.rightcode.wellcar.network.model.response.estimate.EstimateStore;

import java.util.ArrayList;

import lombok.Setter;

public class EstimateCustomerDetailRecyclerViewAdapter extends RecyclerView.Adapter<CommonRecyclerViewHolder> {
    private Context mContext;

    @Setter
    private EstimateStore data;

    public EstimateCustomerDetailRecyclerViewAdapter(Context context) {
        this.mContext = context;
    }

    @NonNull
    @Override
    public CommonRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_estimate_customer_detail_recyclerview, viewGroup, false);
        return new EstimateCustomerDetailViewHolder(view, mContext);
    }

    @Override
    public void onBindViewHolder(@NonNull CommonRecyclerViewHolder viewHolder, int i) {
        ((EstimateCustomerDetailViewHolder) viewHolder).onBind(data);
    }

    @Override
    public int getItemCount() {
        if (data == null) {
            return 0;
        } else {
            return data.getStores().size();
        }
    }
}
