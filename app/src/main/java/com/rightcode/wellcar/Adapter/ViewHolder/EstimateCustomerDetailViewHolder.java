package com.rightcode.wellcar.Adapter.ViewHolder;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.rightcode.wellcar.Activity.CompanyDetailActivity;
import com.rightcode.wellcar.R;
import com.rightcode.wellcar.Util.DataEnums;
import com.rightcode.wellcar.Util.Log;
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
    @BindView(R.id.view_is_payment)
    View view_is_payment;

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
        Glide.with(mContext).load(data.getStores().get(getAdapterPosition()).getThumbnail())
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .centerCrop()
                .apply(RequestOptions.circleCropTransform())
                .into(iv_company_image);
        tv_company_name.setText(data.getStores().get(getAdapterPosition()).getName());
        tv_grade.setText(data.getStores().get(getAdapterPosition()).getRate().toString());
        tv_company_introduction.setText(data.getStores().get(getAdapterPosition()).getIntroduction());
        tv_company_review_count.setText(data.getStores().get(getAdapterPosition()).getReviewCount().toString());
        tv_company_order_count.setText(data.getStores().get(getAdapterPosition()).getOrderCount().toString());

        if (data.getStores().get(getAdapterPosition()).getIsPayment()) {
            view_is_payment.setVisibility(View.VISIBLE);
            tv_estimate_price.setText("결제완료");
            tv_estimate_price.setBackground(mContext.getDrawable(R.drawable.red_border_red_background_corner_5_padding_10));
            tv_estimate_price.setTextColor(mContext.getColor(R.color.white));
        } else {
            view_is_payment.setVisibility(View.GONE);
            tv_estimate_price.setText(data.getStores().get(getAdapterPosition()).getPrice() != null ?
                    MoneyHelper.getUsaUnit(data.getStores().get(getAdapterPosition()).getPrice()) :
                    "견적대기중..");
            tv_estimate_price.setBackground(null);
            tv_estimate_price.setTextColor(mContext.getColor(R.color.black));
        }
    }

    @Override
    public void onClick(View v) {
        // 견적입력이 끝난 경우
        if (!data.getStores().get(getAdapterPosition()).getIsPayment()) {
            if (tv_estimate_price.getText().toString().equals("견적대기중..")) {
                Intent intent = new Intent(mContext, CompanyDetailActivity.class);
                intent.putExtra(EXTRA_ESTIMATE_ID, data.getId());
                intent.putExtra(EXTRA_COMPANY_DETAIL_TYPE, DataEnums.CompanyDetailType.BASIC);
                intent.putExtra(EXTRA_COMPANY_ID, data.getStores().get(getAdapterPosition()).getId());
                intent.putExtra(EXTRA_ESTIMATE_PRICE, data.getStores().get(getAdapterPosition()).getPrice());
                startActivity(intent);
            } else {
                Log.d(data);
                Log.d(data.getStores().get(getAdapterPosition()).getPrice());
                Intent intent = new Intent(mContext, CompanyDetailActivity.class);
                intent.putExtra(EXTRA_ESTIMATE_ID, data.getId());
                intent.putExtra(EXTRA_COMPANY_DETAIL_TYPE, DataEnums.CompanyDetailType.ESTIMATE);
                intent.putExtra(EXTRA_COMPANY_ID, data.getStores().get(getAdapterPosition()).getId());
                intent.putExtra(EXTRA_ESTIMATE_PRICE, data.getStores().get(getAdapterPosition()).getPrice());
                startActivity(intent);
            }
        }
    }
}
