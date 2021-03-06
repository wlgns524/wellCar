package com.rightcode.wellcar.Adapter.ViewHolder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.rightcode.wellcar.Adapter.RecyclerViewAdapter.ReviewImageRecyclerViewAdapter;
import com.rightcode.wellcar.R;
import com.rightcode.wellcar.network.model.response.chatRoom.ChatRoomDetail;

import butterknife.BindView;
import butterknife.ButterKnife;


public class TalkDetailMineViewHolder extends CommonRecyclerViewHolder {

    @BindView(R.id.tv_talk_detail_content)
    TextView tv_talk_detail_content;
    @BindView(R.id.tv_talk_detail_date)
    TextView tv_talk_detail_date;

    private Context mContext;

    public TalkDetailMineViewHolder(View viewHolder, Context context) {
        super(viewHolder, context);
        mContext = context;
        ButterKnife.bind(this, itemView);
    }

    public void onBind(ChatRoomDetail data) {
        tv_talk_detail_content.setText(data.getContent());
        tv_talk_detail_date.setText(data.getCreatedAt());
    }

}
