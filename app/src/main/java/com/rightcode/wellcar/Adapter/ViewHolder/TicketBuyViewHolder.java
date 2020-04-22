package com.rightcode.wellcar.Adapter.ViewHolder;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.rightcode.wellcar.Activity.CompanyDetailActivity;
import com.rightcode.wellcar.Activity.PaymentActivity;
import com.rightcode.wellcar.Adapter.RecyclerViewAdapter.TicketBuyRecyclerViewAdapter;
import com.rightcode.wellcar.Dialog.PaymentMethodDialog;
import com.rightcode.wellcar.R;
import com.rightcode.wellcar.Util.MoneyHelper;
import com.rightcode.wellcar.network.model.response.notice.Notice;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.rightcode.wellcar.Util.ExtraData.EXTRA_PAYMENT_URL;


public class TicketBuyViewHolder extends CommonRecyclerViewHolder implements View.OnClickListener {

    @BindView(R.id.tv_ticket_buy_title)
    TextView tv_ticket_buy_title;
    @BindView(R.id.tv_ticket_buy_price)
    TextView tv_ticket_buy_price;


    private Context mContext;
    private TicketBuyRecyclerViewAdapter.TicketBuy data;

    public TicketBuyViewHolder(View viewHolder, Context context) {
        super(viewHolder, context);
        mContext = context;
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(this);
    }

    public void onBind(TicketBuyRecyclerViewAdapter.TicketBuy data) {
        this.data = data;
        tv_ticket_buy_title.setText(String.format("이용권 %d개", data.getCount()));
        tv_ticket_buy_price.setText(MoneyHelper.getKorUnit(data.getPrice()));
    }

    @Override
    public void onClick(View v) {
        PaymentMethodDialog paymentMethodDialog = new PaymentMethodDialog(mContext, "ticket");
        paymentMethodDialog.setCount(data.getCount());
        paymentMethodDialog.setPrice(data.getPrice());
        paymentMethodDialog.show();
    }
}
