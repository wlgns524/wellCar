package com.rightcode.wellcar.Adapter.ViewHolder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.rightcode.wellcar.R;
import com.rightcode.wellcar.network.model.response.notice.Notice;
import com.rightcode.wellcar.network.model.response.paymentTicket.PaymentTicketList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class BuyTicketViewHolder extends CommonRecyclerViewHolder {

    @BindView(R.id.tv_created)
    TextView tv_created;
    @BindView(R.id.tv_diff)
    TextView tv_diff;
    @BindView(R.id.tv_content)
    TextView tv_content;

    private Context mContext;
    private PaymentTicketList data;

    public BuyTicketViewHolder(View viewHolder, Context context) {
        super(viewHolder, context);
        mContext = context;
        ButterKnife.bind(this, itemView);
    }

    public void onBind(PaymentTicketList data) {
        this.data = data;

        tv_created.setText(data.getCreatedAt());
        if (data.getDiff().equals("충전")) {
            tv_diff.setTextColor(mContext.getColor(R.color.app_color));
        } else {
            tv_diff.setTextColor(mContext.getColor(R.color.red));
        }
        tv_diff.setText(data.getDiff());
        tv_content.setText(data.getContent());
    }
}
