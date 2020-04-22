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
import com.rightcode.wellcar.network.model.response.store.Store;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.rightcode.wellcar.Util.ExtraData.EXTRA_COMPANY_DETAIL_TYPE;
import static com.rightcode.wellcar.Util.ExtraData.EXTRA_COMPANY_ID;


public class AroundViewHolder extends CommonRecyclerViewHolder implements View.OnClickListener {

    @BindView(R.id.iv_company_image)
    ImageView iv_company_image;
    @BindView(R.id.tv_store_name)
    TextView tv_store_name;
    @BindView(R.id.tv_company_review_count)
    TextView tv_company_review_count;
    @BindView(R.id.tv_company_content)
    TextView tv_company_content;
    @BindView(R.id.tv_distance)
    TextView tv_distance;

    private Context mContext;
    private Store data;

    public AroundViewHolder(View viewHolder, Context context) {
        super(viewHolder, context);
        ButterKnife.bind(this, viewHolder);
        mContext = context;
    }

    public void onBind(Store data) {
        this.data = data;
        Glide.with(mContext).load(data.getThumbnail() != null ? data.getThumbnail().getName() : "")
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .centerCrop()
                .into(iv_company_image);

        tv_store_name.setText(data.getName());
        tv_company_review_count.setText(data.getReviewCount().toString());
        tv_company_content.setText(data.getIntroduction());
        tv_distance.setText(data.getDistance());
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(mContext, CompanyDetailActivity.class);
        intent.putExtra(EXTRA_COMPANY_ID, data.getId());
        intent.putExtra(EXTRA_COMPANY_DETAIL_TYPE, DataEnums.CompanyDetailType.BASIC);
        startActivity(intent);
    }
}
