package com.rightcode.wellcar.Adapter.RecyclerViewAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rightcode.wellcar.Adapter.ViewHolder.BuyDetailEstimateViewHolder;
import com.rightcode.wellcar.Adapter.ViewHolder.CommonRecyclerViewHolder;
import com.rightcode.wellcar.R;
import com.rightcode.wellcar.network.model.response.item.Item;
import com.rightcode.wellcar.network.model.response.itemBrand.ItemBrand;
import com.rightcode.wellcar.network.model.response.paymentEstimate.PaymentEstimateDetail;

import lombok.Setter;

public class BuyDetailEstimateRecyclerViewAdapter extends RecyclerView.Adapter<CommonRecyclerViewHolder> {
    private Context mContext;

    @Setter
    private PaymentEstimateDetail data;

    public BuyDetailEstimateRecyclerViewAdapter(Context context) {
        this.mContext = context;
    }

    @NonNull
    @Override
    public CommonRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_buy_detail_item_recyclerview, viewGroup, false);
        return new BuyDetailEstimateViewHolder(view, mContext);
    }

    @Override
    public void onBindViewHolder(@NonNull CommonRecyclerViewHolder viewHolder, int position) {
        if (position == 0) {
            Item item = new Item();
            ItemBrand itemBrand = new ItemBrand();
            itemBrand.setName(data.getEstimateStore().getEstimate().getSi());
            item.setItemBrand(itemBrand);
            item.setName(data.getEstimateStore().getEstimate().getGu());
            ((BuyDetailEstimateViewHolder) viewHolder).onBind(item, true);
        } else {
            ((BuyDetailEstimateViewHolder) viewHolder).onBind(data.getEstimateStore().getEstimate().getItems().get(position - 1), false);
        }
    }


    @Override
    public int getItemCount() {
        if (data == null) {
            return 0;
        } else {
            return data.getEstimateStore().getEstimate().getItems().size() + 1;
        }
    }
}
