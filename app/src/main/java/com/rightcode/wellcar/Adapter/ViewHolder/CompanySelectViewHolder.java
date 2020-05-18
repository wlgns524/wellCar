package com.rightcode.wellcar.Adapter.ViewHolder;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.rightcode.wellcar.Activity.CompanyDetailActivity;
import com.rightcode.wellcar.R;
import com.rightcode.wellcar.RxJava.RxBus;
import com.rightcode.wellcar.RxJava.RxEvent.SelectEvent;
import com.rightcode.wellcar.Util.DataEnums;
import com.rightcode.wellcar.network.model.response.store.Store;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.rightcode.wellcar.Util.ExtraData.EXTRA_COMPANY_DETAIL_TYPE;
import static com.rightcode.wellcar.Util.ExtraData.EXTRA_COMPANY_ID;
import static com.rightcode.wellcar.Util.ExtraData.EXTRA_ESTIMATE_ID;
import static com.rightcode.wellcar.Util.ExtraData.EXTRA_ESTIMATE_PRICE;


public class CompanySelectViewHolder extends CommonRecyclerViewHolder implements View.OnClickListener {

    @BindView(R.id.ll_background)
    LinearLayout ll_background;
    @BindView(R.id.iv_company_image)
    ImageView iv_company_image;
    @BindView(R.id.tv_store_name)
    TextView tv_store_name;
    @BindView(R.id.tv_grade)
    TextView tv_grade;
    @BindView(R.id.tv_company_review_count)
    TextView tv_company_review_count;
    @BindView(R.id.tv_company_order_count)
    TextView tv_company_order_count;
    @BindView(R.id.tv_company_introduction)
    TextView tv_company_introduction;

    private Context mContext;
    private Store data;

    public CompanySelectViewHolder(View viewHolder, Context context) {
        super(viewHolder, context);
        mContext = context;
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(this);
    }

    public void onBind(Store data) {
        this.data = data;
        initLayout();
    }

    private void initLayout() {
        Glide.with(mContext).load(data.getThumbnail() != null ? data.getThumbnail().getName() : "")
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .centerCrop()
                .into(iv_company_image);
        tv_store_name.setText(data.getName());
        tv_grade.setText(data.getRate().toString());
        tv_company_review_count.setText(data.getReviewCount().toString());
        tv_company_order_count.setText(data.getOrderCount().toString());
        tv_company_introduction.setText(data.getIntroduction());
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(mContext, CompanyDetailActivity.class);
        intent.putExtra(EXTRA_COMPANY_ID, data.getId());
        intent.putExtra(EXTRA_COMPANY_DETAIL_TYPE, DataEnums.CompanyDetailType.DIRECT_ESTIMATE);
        startActivity(intent);
    }
}
