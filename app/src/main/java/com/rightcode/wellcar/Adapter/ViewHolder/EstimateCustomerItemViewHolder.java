package com.rightcode.wellcar.Adapter.ViewHolder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.rightcode.wellcar.R;
import com.rightcode.wellcar.network.model.response.estimate.Estimate;
import com.rightcode.wellcar.network.model.response.item.Item;

import butterknife.BindView;
import butterknife.ButterKnife;


public class EstimateCustomerItemViewHolder extends CommonRecyclerViewHolder {

    @BindView(R.id.tv_item_brand_title)
    TextView tv_item_brand_title;
    @BindView(R.id.tv_item_brand_content)
    TextView tv_item_brand_content;
    @BindView(R.id.tv_item_kind_title)
    TextView tv_item_kind_title;
    @BindView(R.id.tv_item_kind_content)
    TextView tv_item_kind_content;

    private Context mContext;

    public EstimateCustomerItemViewHolder(View viewHolder, Context context) {
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
