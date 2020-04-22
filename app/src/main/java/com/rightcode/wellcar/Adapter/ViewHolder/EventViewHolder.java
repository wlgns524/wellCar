package com.rightcode.wellcar.Adapter.ViewHolder;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;


import com.bumptech.glide.Glide;
import com.rightcode.wellcar.Activity.EventDetailActivity;
import com.rightcode.wellcar.R;
import com.rightcode.wellcar.network.model.response.event.Event;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.rightcode.wellcar.Util.ExtraData.EXTRA_EVENT_ID;


public class EventViewHolder extends CommonRecyclerViewHolder implements View.OnClickListener {

    @BindView(R.id.iv_event)
    ImageView iv_event;

    private Context mContext;
    private Event data;

    public EventViewHolder(View viewHolder, Context context) {
        super(viewHolder, context);
        mContext = context;
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(this);

    }


    public void onBind(Event data) {
        this.data = data;
        Glide.with(mContext)
                .load(data.getThumbnail())
                .into(iv_event);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(mContext, EventDetailActivity.class);
        intent.putExtra(EXTRA_EVENT_ID, data.getId());
        startActivity(intent);
    }
}
