package com.rightcode.wellcar.Adapter.ViewHolder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.rightcode.wellcar.R;
import com.rightcode.wellcar.RxJava.RxBus;
import com.rightcode.wellcar.RxJava.RxEvent.AroundSelectEvent;

import butterknife.BindView;
import butterknife.ButterKnife;


public class AroundHeaderItemViewHolder extends CommonRecyclerViewHolder {

    @BindView(R.id.tv_around_header_item)
    TextView tv_around_header_item;
    private Context mContext;

    public AroundHeaderItemViewHolder(View viewHolder, Context context) {
        super(viewHolder, context);
        mContext = context;

        ButterKnife.bind(this, itemView);
    }

    public void onBind(String title, Boolean isSelect) {
        tv_around_header_item.setText(title);
        if (isSelect) {
            tv_around_header_item.setBackgroundResource(R.drawable.app_color_border_white_background_corner_5);
            tv_around_header_item.setTextColor(mContext.getColor(R.color.app_color));
        } else {
            tv_around_header_item.setBackgroundResource(R.drawable.space_border_space_corner_5);
            tv_around_header_item.setTextColor(mContext.getColor(R.color.text_gray));
        }
    }
}
