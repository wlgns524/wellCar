package com.rightcode.wellcar.Adapter.ViewHolder;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

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


public class CompanyViewHolder extends CommonRecyclerViewHolder {

    @BindView(R.id.ll_background)
    LinearLayout ll_background;

    private Context mContext;
    private Store data;
    private Boolean isSelect = false;
    private Integer position;

    public CompanyViewHolder(View viewHolder, Context context) {
        super(viewHolder, context);
        mContext = context;
        ButterKnife.bind(this, itemView);
    }

    public void onBind(Store data, Integer position) {
        this.data = data;
        this.position = position;
        initLayout();
    }

    private void initLayout(){

    }

    @OnClick({R.id.ll_background, R.id.tv_store_detail})
    void onClickMenu(View view) {
        switch (view.getId()) {
            case R.id.ll_background: {
                if (!isSelect) {
                    ll_background.setBackground(mContext.getDrawable(R.drawable.app_color_border_white_background_corner_5));
                    isSelect = true;
                } else {
                    ll_background.setBackground(mContext.getDrawable(R.drawable.space_border_white_corner_5));
                    isSelect = false;
                }
                RxBus.send(new SelectEvent(position, isSelect));
                break;
            }
            case R.id.tv_store_detail: {
                Intent intent = new Intent(mContext, CompanyDetailActivity.class);
                intent.putExtra(EXTRA_COMPANY_ID, data.getId());
                intent.putExtra(EXTRA_COMPANY_DETAIL_TYPE, DataEnums.CompanyDetailType.BASIC);
                startActivity(intent);
                break;
            }
        }
    }
}
