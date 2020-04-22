package com.rightcode.wellcar.Adapter.ViewHolder;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.rightcode.wellcar.Activity.CompanyDetailActivity;
import com.rightcode.wellcar.R;
import com.rightcode.wellcar.Util.DataEnums;
import com.rightcode.wellcar.Util.MoneyHelper;
import com.rightcode.wellcar.network.model.response.estimate.EstimateStore;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.rightcode.wellcar.Util.ExtraData.EXTRA_COMPANY_DETAIL_TYPE;
import static com.rightcode.wellcar.Util.ExtraData.EXTRA_COMPANY_ID;
import static com.rightcode.wellcar.Util.ExtraData.EXTRA_ESTIMATE_ID;
import static com.rightcode.wellcar.Util.ExtraData.EXTRA_ESTIMATE_PRICE;


public class EstimateCustomerDetailViewHolder extends CommonRecyclerViewHolder implements View.OnClickListener {

    @BindView(R.id.iv_company_image)
    ImageView iv_company_image;
    @BindView(R.id.tv_company_name)
    TextView tv_company_name;
    @BindView(R.id.tv_grade)
    TextView tv_grade;
    @BindView(R.id.tv_company_introduction)
    TextView tv_company_introduction;
    @BindView(R.id.tv_company_review_count)
    TextView tv_company_review_count;
    @BindView(R.id.tv_company_order_count)
    TextView tv_company_order_count;
    @BindView(R.id.tv_estimate_price)
    TextView tv_estimate_price;

    private Context mContext;
    private EstimateStore data;

    public EstimateCustomerDetailViewHolder(View viewHolder, Context context) {
        super(viewHolder, context);
        mContext = context;
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(this);
    }


    public void onBind(EstimateStore data) {
        this.data = data;
        Glide.with(mContext).load(data.getStores().get(getAdapterPosition()).getThumbnail() != null ? data.getStores().get(getAdapterPosition()).getThumbnail().getName() : "")
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .centerCrop()
                .into(iv_company_image);
        tv_company_name.setText(data.getStores().get(getAdapterPosition()).getName());
        tv_grade.setText(data.getStores().get(getAdapterPosition()).getRate().toString());
        tv_company_introduction.setText(data.getStores().get(getAdapterPosition()).getIntroduction());
        tv_company_review_count.setText(data.getStores().get(getAdapterPosition()).getReviewCount().toString());
        tv_company_order_count.setText(data.getStores().get(getAdapterPosition()).getOrderCount().toString());
        tv_estimate_price.setText(
                data.getStores().get(getAdapterPosition()).getEstimateStore().getPrice() != null ?
                        MoneyHelper.getUsaUnit(data.getStores().get(getAdapterPosition()).getEstimateStore().getPrice()) : "견적대기중..");
    }

    @Override
    public void onClick(View v) {
        if (tv_estimate_price.getText().toString().equals("견적대기중..")) {
            Intent intent = new Intent(mContext, CompanyDetailActivity.class);
            intent.putExtra(EXTRA_ESTIMATE_ID, data.getId());
            intent.putExtra(EXTRA_COMPANY_DETAIL_TYPE, DataEnums.CompanyDetailType.BASIC);
            intent.putExtra(EXTRA_COMPANY_ID, data.getStores().get(getAdapterPosition()).getId());
            intent.putExtra(EXTRA_ESTIMATE_PRICE, data.getStores().get(getAdapterPosition()).getEstimateStore().getPrice());
            startActivity(intent);
        } else {
            Intent intent = new Intent(mContext, CompanyDetailActivity.class);
            intent.putExtra(EXTRA_ESTIMATE_ID, data.getId());
            intent.putExtra(EXTRA_COMPANY_DETAIL_TYPE, DataEnums.CompanyDetailType.ESTIMATE);
            intent.putExtra(EXTRA_COMPANY_ID, data.getStores().get(getAdapterPosition()).getId());
            intent.putExtra(EXTRA_ESTIMATE_PRICE, data.getStores().get(getAdapterPosition()).getEstimateStore().getPrice());
            startActivity(intent);
        }
    }
}
