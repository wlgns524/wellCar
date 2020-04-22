package com.rightcode.wellcar.Adapter.RecyclerViewAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rightcode.wellcar.Adapter.ViewHolder.BuyTicketHeaderViewHolder;
import com.rightcode.wellcar.Adapter.ViewHolder.BuyTicketViewHolder;
import com.rightcode.wellcar.Adapter.ViewHolder.CommonRecyclerViewHolder;
import com.rightcode.wellcar.R;
import com.rightcode.wellcar.network.model.response.paymentTicket.PaymentTicketList;

import java.util.ArrayList;

import lombok.Setter;

public class BuyTicketRecyclerViewAdapter extends RecyclerView.Adapter<CommonRecyclerViewHolder> {
    private Context mContext;

    private final Integer TYPE_HEAD = 0;
    private final Integer TYPE_ITEM = 1;

    @Setter
    private ArrayList<PaymentTicketList> data;

    public BuyTicketRecyclerViewAdapter(Context context) {
        this.mContext = context;
    }

    @NonNull
    @Override
    public CommonRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == TYPE_HEAD) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_buy_ticket_head_recyclerview, viewGroup, false);
            return new BuyTicketHeaderViewHolder(view, mContext);
        } else {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_buy_ticket_recyclerview, viewGroup, false);
            return new BuyTicketViewHolder(view, mContext);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull CommonRecyclerViewHolder viewHolder, int position) {
        if (viewHolder instanceof BuyTicketHeaderViewHolder)
            ((BuyTicketHeaderViewHolder) viewHolder).onBind();
        else if (viewHolder instanceof BuyTicketViewHolder)
            ((BuyTicketViewHolder) viewHolder).onBind(data.get(position - 1));
    }


    @Override
    public int getItemCount() {
        if (data == null) {
            return 1;
        } else if (data != null && data.size() == 0) {
            return 1;
        } else {
            return data.size() + 1;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEAD;
        } else {
            return TYPE_ITEM;
        }
    }
}
