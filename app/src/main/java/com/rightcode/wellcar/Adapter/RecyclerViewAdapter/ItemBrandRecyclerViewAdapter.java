package com.rightcode.wellcar.Adapter.RecyclerViewAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rightcode.wellcar.Adapter.ViewHolder.CommonRecyclerViewHolder;
import com.rightcode.wellcar.Adapter.ViewHolder.CompanyViewHolder;
import com.rightcode.wellcar.Adapter.ViewHolder.ItemBrandViewHolder;
import com.rightcode.wellcar.R;
import com.rightcode.wellcar.RxJava.Event;
import com.rightcode.wellcar.RxJava.RxBus;
import com.rightcode.wellcar.RxJava.RxEvent.SelectEvent;
import com.rightcode.wellcar.Util.ToastUtil;
import com.rightcode.wellcar.network.model.response.itemBrand.ItemBrand;
import com.rightcode.wellcar.network.model.response.store.Store;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

public class ItemBrandRecyclerViewAdapter extends RecyclerView.Adapter<CommonRecyclerViewHolder> {
    private Context mContext;

    @Setter
    private ArrayList<ItemBrand> data;

    public ItemBrandRecyclerViewAdapter(Context context) {
        this.mContext = context;
    }

    @NonNull
    @Override
    public CommonRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_item_brand_recyclerview, viewGroup, false);
        return new ItemBrandViewHolder(view, mContext);
    }

    @Override
    public void onBindViewHolder(@NonNull CommonRecyclerViewHolder viewHolder, int position) {
        ((ItemBrandViewHolder) viewHolder).onBind(data.get(position));
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
