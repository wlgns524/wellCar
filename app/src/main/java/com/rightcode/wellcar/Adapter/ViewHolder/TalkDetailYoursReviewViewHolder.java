package com.rightcode.wellcar.Adapter.ViewHolder;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rightcode.wellcar.Activity.ReviewWriteActivity;
import com.rightcode.wellcar.R;
import com.rightcode.wellcar.network.model.response.chatRoom.ChatRoomDetail;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.rightcode.wellcar.Util.ExtraData.EXTRA_COMPANY_ID;


public class TalkDetailYoursReviewViewHolder extends CommonRecyclerViewHolder implements View.OnClickListener {

    @BindView(R.id.iv_talk_detail_yours)
    ImageView iv_talk_detail_yours;
    @BindView(R.id.tv_talk_detail_date)
    TextView tv_talk_detail_date;

    private Context mContext;
    private Integer companyId;
    private String carBrandImage;

    public TalkDetailYoursReviewViewHolder(View viewHolder, Context context, Integer companyId, String carBrandImage) {
        super(viewHolder, context);
        mContext = context;
        this.companyId = companyId;
        this.carBrandImage = carBrandImage;
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(this);
    }

    public void onBind(ChatRoomDetail data) {
        Glide.with(mContext)
                .load(carBrandImage)
                .into(iv_talk_detail_yours);

        tv_talk_detail_date.setText(data.getCreatedAt());
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(mContext, ReviewWriteActivity.class);
        intent.putExtra(EXTRA_COMPANY_ID, companyId);
        startActivity(intent);
    }
}
