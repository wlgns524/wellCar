package com.rightcode.wellcar.Adapter.ViewHolder;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.rightcode.wellcar.Activity.Setting.NoticeDetailActivity;
import com.rightcode.wellcar.R;
import com.rightcode.wellcar.network.model.response.notice.Notice;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.rightcode.wellcar.Util.ExtraData.EXTRA_NOTICE_ID;


public class NoticeViewHolder extends CommonRecyclerViewHolder implements View.OnClickListener {

    @BindView(R.id.tv_notice_date)
    TextView tv_notice_date;
    @BindView(R.id.tv_notice_title)
    TextView tv_notice_title;

    private Context mContext;
    private Notice data;

    public NoticeViewHolder(View viewHolder, Context context) {
        super(viewHolder, context);
        mContext = context;
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(this);
    }

    public void onBind(Notice data) {
        this.data = data;
        tv_notice_date.setText(data.getCreatedAt());
        tv_notice_title.setText(data.getTitle());
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(mContext, NoticeDetailActivity.class);
        intent.putExtra(EXTRA_NOTICE_ID, data.getId());
        startActivity(intent);
    }
}
