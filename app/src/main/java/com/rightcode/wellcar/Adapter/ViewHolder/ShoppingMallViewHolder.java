package com.rightcode.wellcar.Adapter.ViewHolder;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.rightcode.wellcar.Activity.CompanyDetailActivity;
import com.rightcode.wellcar.Activity.Setting.NoticeDetailActivity;
import com.rightcode.wellcar.R;
import com.rightcode.wellcar.Util.Log;
import com.rightcode.wellcar.network.model.response.notice.Notice;
import com.rightcode.wellcar.network.model.response.shoppingMall.ShoppingMallList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.rightcode.wellcar.Util.ExtraData.EXTRA_NOTICE_ID;


public class ShoppingMallViewHolder extends CommonRecyclerViewHolder implements View.OnClickListener {

    @BindView(R.id.iv_shopping_mall)
    ImageView iv_shopping_mall;

    private Context mContext;
    private ShoppingMallList data;

    public ShoppingMallViewHolder(View viewHolder, Context context) {
        super(viewHolder, context);
        mContext = context;
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(this);
    }

    public void onBind(ShoppingMallList data) {
        this.data = data;

        Glide.with(mContext)
                .load(data.getThumbnail())
                .apply(new RequestOptions().transforms(new RoundedCorners(8)))
                .into(iv_shopping_mall);
    }

    @Override
    public void onClick(View v) {
        if (!TextUtils.isEmpty(data.getUrl())) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(data.getUrl())));
        }
    }
}
