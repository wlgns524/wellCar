package com.rightcode.wellcar.Adapter.RecyclerViewAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rightcode.wellcar.Adapter.ViewHolder.CommonRecyclerViewHolder;
import com.rightcode.wellcar.Adapter.ViewHolder.SettlementManagementViewHolder;
import com.rightcode.wellcar.R;
import com.rightcode.wellcar.network.model.response.accountCompany.AccountCompanyList;

import java.util.ArrayList;

import lombok.Setter;

public class CarWashManagementSettlementManagementRecyclerViewAdapter extends RecyclerView.Adapter<CommonRecyclerViewHolder> {

    private Context mContext;

    @Setter
    private ArrayList<AccountCompanyList> data;

    public CarWashManagementSettlementManagementRecyclerViewAdapter(Context context) {
        this.mContext = context;
    }

    @NonNull
    @Override
    public CommonRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_settlement_management_recyclerview, viewGroup, false);
        return new SettlementManagementViewHolder(view, mContext);
    }

    @Override
    public void onBindViewHolder(@NonNull CommonRecyclerViewHolder viewHolder, int position) {
        ((SettlementManagementViewHolder) viewHolder).onBind(data.get(position));
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
