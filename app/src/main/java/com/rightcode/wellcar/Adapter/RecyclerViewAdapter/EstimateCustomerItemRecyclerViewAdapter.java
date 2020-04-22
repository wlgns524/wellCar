package com.rightcode.wellcar.Adapter.RecyclerViewAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rightcode.wellcar.Adapter.ViewHolder.CommonRecyclerViewHolder;
import com.rightcode.wellcar.Adapter.ViewHolder.EstimateCustomerItemViewHolder;
import com.rightcode.wellcar.R;
import com.rightcode.wellcar.network.model.response.brand.Brand;
import com.rightcode.wellcar.network.model.response.estimate.Estimate;
import com.rightcode.wellcar.network.model.response.item.Item;
import com.rightcode.wellcar.network.model.response.itemBrand.ItemBrand;

import java.util.ArrayList;

import lombok.Setter;

public class EstimateCustomerItemRecyclerViewAdapter extends RecyclerView.Adapter<CommonRecyclerViewHolder> {

    private Context mContext;

    @Setter
    private Estimate data;

    public EstimateCustomerItemRecyclerViewAdapter(Context context) {
        this.mContext = context;
    }

    @NonNull
    @Override
    public CommonRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_estimate_customer_item_recyclerview, viewGroup, false);
        return new EstimateCustomerItemViewHolder(view, mContext);
    }

    @Override
    public void onBindViewHolder(@NonNull CommonRecyclerViewHolder viewHolder, int position) {
        if (position == 0) {
            Item item = new Item();
            ItemBrand itemBrand = new ItemBrand();
            itemBrand.setName(data.getSi());
            item.setItemBrand(itemBrand);
            item.setName(data.getGu());
            ((EstimateCustomerItemViewHolder) viewHolder).onBind(item, true);
        } else {
            ((EstimateCustomerItemViewHolder) viewHolder).onBind(data.getItems().get(position - 1), false);
        }
    }


    @Override
    public int getItemCount() {
        if (data == null) {
            return 0;
        } else {
            return data.getItems().size() + 1;
        }
    }
}
