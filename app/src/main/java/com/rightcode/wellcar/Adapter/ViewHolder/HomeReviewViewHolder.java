package com.rightcode.wellcar.Adapter.ViewHolder;

import android.content.Context;
import android.view.View;

import butterknife.ButterKnife;


public class HomeReviewViewHolder extends CommonRecyclerViewHolder {


    private Context mContext;

    public HomeReviewViewHolder(View viewHolder, Context context) {
        super(viewHolder, context);
        mContext = context;
        ButterKnife.bind(this, itemView);

    }


    public void onBind(String data) {
    }

}
