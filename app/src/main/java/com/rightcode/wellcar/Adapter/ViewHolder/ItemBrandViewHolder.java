package com.rightcode.wellcar.Adapter.ViewHolder;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.rightcode.wellcar.Activity.CompanyDetailActivity;
import com.rightcode.wellcar.Activity.CompanySelectActivity;
import com.rightcode.wellcar.R;
import com.rightcode.wellcar.RxJava.RxBus;
import com.rightcode.wellcar.RxJava.RxEvent.SelectEvent;
import com.rightcode.wellcar.Util.DataEnums;
import com.rightcode.wellcar.network.model.response.itemBrand.ItemBrand;
import com.rightcode.wellcar.network.model.response.store.Store;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.rightcode.wellcar.Util.ExtraData.EXTRA_BRAND_ID;
import static com.rightcode.wellcar.Util.ExtraData.EXTRA_COMPANY_DETAIL_TYPE;
import static com.rightcode.wellcar.Util.ExtraData.EXTRA_COMPANY_ID;


public class ItemBrandViewHolder extends CommonRecyclerViewHolder implements View.OnClickListener {

    @BindView(R.id.iv_item_brand)
    ImageView iv_item_brand;

    private Context mContext;
    private ItemBrand data;

    public ItemBrandViewHolder(View viewHolder, Context context) {
        super(viewHolder, context);
        mContext = context;
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(this);
    }

    public void onBind(ItemBrand data) {
        this.data = data;
        initLayout();
    }

    private void initLayout() {
        Glide.with(mContext).load(data.getImageName())
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(iv_item_brand);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(mContext, CompanySelectActivity.class);
        intent.putExtra(EXTRA_BRAND_ID, data.getId());
        startActivity(intent);
    }
}
