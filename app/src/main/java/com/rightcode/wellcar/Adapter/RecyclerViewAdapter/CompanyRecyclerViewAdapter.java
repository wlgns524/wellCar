package com.rightcode.wellcar.Adapter.RecyclerViewAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rightcode.wellcar.Adapter.ViewHolder.CommonRecyclerViewHolder;
import com.rightcode.wellcar.Adapter.ViewHolder.CompanyViewHolder;
import com.rightcode.wellcar.R;

import java.util.ArrayList;

import lombok.Setter;

public class CompanyRecyclerViewAdapter extends RecyclerView.Adapter<CommonRecyclerViewHolder> {
    private Context mContext;

    @Setter
    private ArrayList<String> data;

    public CompanyRecyclerViewAdapter(Context context) {
        this.mContext = context;
    }

    @NonNull
    @Override
    public CommonRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_company_recyclerview, viewGroup, false);
        return new CompanyViewHolder(view, mContext);
    }

    @Override
    public void onBindViewHolder(@NonNull CommonRecyclerViewHolder viewHolder, int i) {
        ((CompanyViewHolder) viewHolder).onBind("");
    }

    @Override
    public int getItemCount() {
        if (data == null) {
            return 15;
        } else {
            return data.size();
        }
    }

}
