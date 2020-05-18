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
import com.rightcode.wellcar.Activity.TalkDetailCompanyActivity;
import com.rightcode.wellcar.Dialog.CommonDialog;
import com.rightcode.wellcar.Dialog.EstimateInputDialog;
import com.rightcode.wellcar.R;
import com.rightcode.wellcar.RxJava.RxBus;
import com.rightcode.wellcar.RxJava.RxEvent.MoveTalkEvent;
import com.rightcode.wellcar.Util.DataEnums;
import com.rightcode.wellcar.Util.MoneyHelper;
import com.rightcode.wellcar.network.model.response.estimate.EstimateStore;
import com.rightcode.wellcar.network.model.response.estimateStore.EstimateStoreList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.rightcode.wellcar.Util.ExtraData.EXTRA_COMPANY_DETAIL_TYPE;
import static com.rightcode.wellcar.Util.ExtraData.EXTRA_COMPANY_ID;
import static com.rightcode.wellcar.Util.ExtraData.EXTRA_COMPANY_NAME;
import static com.rightcode.wellcar.Util.ExtraData.EXTRA_ESTIMATE_ID;
import static com.rightcode.wellcar.Util.ExtraData.EXTRA_ESTIMATE_PRICE;


public class CompanyEstimateManagementViewHolder extends CommonRecyclerViewHolder implements View.OnClickListener {

    @BindView(R.id.iv_car_logo)
    ImageView iv_car_logo;
    @BindView(R.id.iv_car)
    ImageView iv_car;
    @BindView(R.id.tv_new)
    TextView tv_new;
    @BindView(R.id.tv_car_brand_name)
    TextView tv_car_brand_name;
    @BindView(R.id.tv_car_year)
    TextView tv_car_year;
    @BindView(R.id.tv_user_name)
    TextView tv_user_name;
    @BindView(R.id.tv_items)
    TextView tv_items;
    @BindView(R.id.tv_created)
    TextView tv_created;
    @BindView(R.id.ll_price)
    LinearLayout ll_price;
    @BindView(R.id.tv_price)
    TextView tv_price;
    @BindView(R.id.tv_request)
    TextView tv_request;

    private Context mContext;
    private EstimateStoreList data;

    public CompanyEstimateManagementViewHolder(View viewHolder, Context context) {
        super(viewHolder, context);
        mContext = context;
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(this);
    }


    public void onBind(EstimateStoreList data) {
        this.data = data;
        Glide.with(mContext)
                .load(data.getCarBrandImageName())
                .centerCrop()
                .override(36, 36)
                .apply(RequestOptions.circleCropTransform())
                .into(iv_car_logo);

        Glide.with(mContext)
                .load(data.getCarImageName())
                .into(iv_car);
        tv_car_brand_name.setText(data.getCarName());
        tv_car_year.setText(data.getCarModelYear());
        tv_user_name.setText(data.getUserName());
        tv_items.setText(data.getItems());
        tv_created.setText(data.getCreatedAt());
        tv_request.setText(data.getRequest());
        if (data.getPrice() != null) {
            tv_new.setVisibility(View.INVISIBLE);
            ll_price.setVisibility(View.VISIBLE);
            tv_price.setText(MoneyHelper.getKorUnit(data.getPrice()));
        } else {
            tv_new.setVisibility(View.VISIBLE);
            ll_price.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        if (data.getPrice() == null) {
            EstimateInputDialog dialog = new EstimateInputDialog(mContext, data.getId());
            dialog.show();
        } else {
            CommonDialog commonDialog = new CommonDialog(mContext);
            commonDialog.setMessage("웰톡으로 이동하시겠습니까?");
            commonDialog.setPositiveButton("확인", ok -> {
                RxBus.send(new MoveTalkEvent());
                commonDialog.dismiss();
            });
            commonDialog.setNegativeButton("취소", cancel -> {
                commonDialog.dismiss();
            });
            commonDialog.show();
        }
    }
}
