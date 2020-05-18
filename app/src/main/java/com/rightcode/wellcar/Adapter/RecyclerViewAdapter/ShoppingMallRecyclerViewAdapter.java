package com.rightcode.wellcar.Adapter.RecyclerViewAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rightcode.wellcar.Adapter.ViewHolder.CommonRecyclerViewHolder;
import com.rightcode.wellcar.Adapter.ViewHolder.NoticeViewHolder;
import com.rightcode.wellcar.Adapter.ViewHolder.ShoppingMallViewHolder;
import com.rightcode.wellcar.R;
import com.rightcode.wellcar.network.model.response.notice.Notice;
import com.rightcode.wellcar.network.model.response.shoppingMall.ShoppingMallList;

import java.util.ArrayList;

import lombok.Setter;

public class ShoppingMallRecyclerViewAdapter extends RecyclerView.Adapter<CommonRecyclerViewHolder> {
    private Context mContext;

    @Setter
    private ArrayList<ShoppingMallList> data;

    public ShoppingMallRecyclerViewAdapter(Context context) {
        this.mContext = context;
    }

    @NonNull
    @Override
    public CommonRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_shopping_mall_recyclerview, viewGroup, false);
        return new ShoppingMallViewHolder(view, mContext);
    }

    @Override
    public void onBindViewHolder(@NonNull CommonRecyclerViewHolder viewHolder, int position) {
        ((ShoppingMallViewHolder) viewHolder).onBind(data.get(position));
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
