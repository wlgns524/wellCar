package com.rightcode.wellcar.Adapter.ViewHolder;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.rightcode.wellcar.Activity.Setting.BuyDetailActivity;
import com.rightcode.wellcar.Activity.Setting.NoticeDetailActivity;
import com.rightcode.wellcar.R;
import com.rightcode.wellcar.Util.Log;
import com.rightcode.wellcar.Util.MoneyHelper;
import com.rightcode.wellcar.network.model.response.notice.Notice;
import com.rightcode.wellcar.network.model.response.paymentEstimate.PaymentEstimateList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.rightcode.wellcar.Util.ExtraData.EXTRA_NOTICE_ID;
import static com.rightcode.wellcar.Util.ExtraData.EXTRA_PAYMENT_ID;


public class BuyEstimateViewHolder extends CommonRecyclerViewHolder implements View.OnClickListener {

    @BindView(R.id.iv_company_image)
    ImageView iv_company_image;
    @BindView(R.id.tv_company_name)
    TextView tv_company_name;
    @BindView(R.id.tv_estimate_time)
    TextView tv_estimate_time;
    @BindView(R.id.tv_estimate_price)
    TextView tv_estimate_price;

    private Context mContext;
    private PaymentEstimateList data;

    public BuyEstimateViewHolder(View viewHolder, Context context) {
        super(viewHolder, context);
        mContext = context;
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(this);
    }

    public void onBind(PaymentEstimateList data) {
        this.data = data;

        Glide.with(mContext).load(data.getEstimateStore().getStore() != null ? data.getEstimateStore().getStore().getThumbnail().getName() : "")
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .centerCrop()
                .apply(RequestOptions.circleCropTransform())
                .into(iv_company_image);
        tv_company_name.setText(data.getEstimateStore().getStore().getName());
        tv_estimate_time.setText(data.getCreatedAt());
        tv_estimate_price.setText(MoneyHelper.getUsaUnit(data.getPrice()));
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(mContext, BuyDetailActivity.class);
        intent.putExtra(EXTRA_PAYMENT_ID, data.getId());
        startActivity(intent);
    }
}
