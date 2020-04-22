package com.rightcode.wellcar.Adapter.ViewHolder;

import android.content.Context;
import android.view.View;

import com.rightcode.wellcar.network.model.response.notice.Notice;

import butterknife.ButterKnife;


public class BuyTicketHeaderViewHolder extends CommonRecyclerViewHolder {


    private Context mContext;

    public BuyTicketHeaderViewHolder(View viewHolder, Context context) {
        super(viewHolder, context);
        mContext = context;
    }

    public void onBind() {
    }

}
