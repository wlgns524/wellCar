package com.rightcode.wellcar.Adapter.ViewHolder;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.rightcode.wellcar.Activity.Setting.BuyDetailActivity;
import com.rightcode.wellcar.R;
import com.rightcode.wellcar.Util.MoneyHelper;
import com.rightcode.wellcar.network.model.response.item.Item;
import com.rightcode.wellcar.network.model.response.paymentEstimate.PaymentEstimateList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.rightcode.wellcar.Util.ExtraData.EXTRA_PAYMENT_ID;


public class BuyDetailEstimateViewHolder extends CommonRecyclerViewHolder {

    @BindView(R.id.tv_item_brand_title)
    TextView tv_item_brand_title;
    @BindView(R.id.tv_item_brand_content)
    TextView tv_item_brand_content;
    @BindView(R.id.tv_item_kind_title)
    TextView tv_item_kind_title;
    @BindView(R.id.tv_item_kind_content)
    TextView tv_item_kind_content;

    private Context mContext;
    private Integer storeId;

    public BuyDetailEstimateViewHolder(View viewHolder, Context context) {
        super(viewHolder, context);
        mContext = context;
        ButterKnife.bind(this, itemView);

    }

    public void onBind(Item data, Boolean isHeader) {
        if (isHeader) {
            tv_item_brand_title.setText("시 선택");
            tv_item_brand_content.setText(data.getItemBrand().getName());
            tv_item_kind_title.setText("구 선택");
            tv_item_kind_content.setText(data.getName());
        } else {
            tv_item_brand_title.setText(data.getDiff().toString());
            tv_item_brand_content.setText(data.getItemBrand().getName());
            tv_item_kind_title.setText(String.format("%s 종류", data.getDiff().toString()));
            tv_item_kind_content.setText(data.getName());
        }
    }
}
