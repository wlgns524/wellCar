package com.rightcode.wellcar.Adapter.RecyclerViewAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rightcode.wellcar.Adapter.ViewHolder.CommonRecyclerViewHolder;
import com.rightcode.wellcar.Adapter.ViewHolder.TicketBuyViewHolder;
import com.rightcode.wellcar.R;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

public class TicketBuyRecyclerViewAdapter extends RecyclerView.Adapter<CommonRecyclerViewHolder> {
    private Context mContext;

    @Setter
    private ArrayList<TicketBuy> data;

    public TicketBuyRecyclerViewAdapter(Context context) {
        this.mContext = context;
        data = new ArrayList<>();
        data.add(new TicketBuy(1, 5000));
        data.add(new TicketBuy(2, 10000));
        data.add(new TicketBuy(3, 15000));
        data.add(new TicketBuy(4, 20000));
        data.add(new TicketBuy(5, 25000));
        data.add(new TicketBuy(10, 49000));
    }

    @NonNull
    @Override
    public CommonRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_ticket_buy_recyclerview, viewGroup, false);
        return new TicketBuyViewHolder(view, mContext);
    }

    @Override
    public void onBindViewHolder(@NonNull CommonRecyclerViewHolder viewHolder, int position) {
        ((TicketBuyViewHolder) viewHolder).onBind(data.get(position));
    }


    @Override
    public int getItemCount() {
        if (data == null) {
            return 0;
        } else {
            return data.size();
        }
    }

    public class TicketBuy{
        @Getter
        private Integer count;
        @Getter
        private Integer price;

        public TicketBuy(Integer count, Integer price) {
            this.count = count;
            this.price = price;
        }
    }
}
