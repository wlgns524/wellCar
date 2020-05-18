package com.rightcode.wellcar.Adapter.ViewHolder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rightcode.wellcar.Adapter.RecyclerViewAdapter.ReviewImageRecyclerViewAdapter;
import com.rightcode.wellcar.R;
import com.rightcode.wellcar.network.model.response.chatRoom.ChatRoomDetail;

import butterknife.BindView;
import butterknife.ButterKnife;


public class TalkDetailYoursViewHolder extends CommonRecyclerViewHolder {

    @BindView(R.id.iv_talk_detail_yours)
    ImageView iv_talk_detail_yours;
    @BindView(R.id.tv_talk_detail_content)
    TextView tv_talk_detail_content;
    @BindView(R.id.tv_talk_detail_date)
    TextView tv_talk_detail_date;

    private Context mContext;
    private String carBrandImage;

    public TalkDetailYoursViewHolder(View viewHolder, Context context, String carBrandImage) {
        super(viewHolder, context);
        mContext = context;
        this.carBrandImage = carBrandImage;
        ButterKnife.bind(this, itemView);
    }

    public void onBind(ChatRoomDetail data) {
        Glide.with(mContext)
                .load(carBrandImage)
                .into(iv_talk_detail_yours);
        tv_talk_detail_content.setText(data.getContent());
        tv_talk_detail_date.setText(data.getCreatedAt());
    }
}
