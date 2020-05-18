package com.rightcode.wellcar.Adapter.RecyclerViewAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rightcode.wellcar.Adapter.ViewHolder.BuyEstimateViewHolder;
import com.rightcode.wellcar.Adapter.ViewHolder.CommonRecyclerViewHolder;
import com.rightcode.wellcar.Adapter.ViewHolder.CompanyEstimateManagementViewHolder;
import com.rightcode.wellcar.R;
import com.rightcode.wellcar.network.model.response.estimateStore.EstimateStoreList;
import com.rightcode.wellcar.network.model.response.paymentEstimate.PaymentEstimateList;

import java.util.ArrayList;

import lombok.Setter;

public class CompanyEstimateManagementRecyclerViewAdapter extends RecyclerView.Adapter<CommonRecyclerViewHolder> {
    private Context mContext;

    @Setter
    private ArrayList<EstimateStoreList> data;

    public CompanyEstimateManagementRecyclerViewAdapter(Context context) {
        this.mContext = context;
    }

    @NonNull
    @Override
    public CommonRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_company_estimate_recyclerview, viewGroup, false);
        return new CompanyEstimateManagementViewHolder(view, mContext);
    }

    @Override
    public void onBindViewHolder(@NonNull CommonRecyclerViewHolder viewHolder, int position) {
        ((CompanyEstimateManagementViewHolder) viewHolder).onBind(data.get(position));
    }


    @Override
    public int getItemCount() {
        if (data == null) {
            return 0;
        } else {
            return data.size();
        }
    }

}
