package com.rightcode.wellcar.Adapter.ViewHolder;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import com.rightcode.wellcar.R;
import com.rightcode.wellcar.network.model.response.store.Store;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class EstimateCompanyViewHolder extends CommonRecyclerViewHolder {


    private Context mContext;

    public EstimateCompanyViewHolder(View viewHolder, Context context) {
        super(viewHolder, context);
        mContext = context;
        ButterKnife.bind(this, itemView);

    }


    public void onBind(Store data) {
    }

    @OnClick({R.id.tv_store_name})
    void onClickMenu(View view) {
        switch (view.getId()) {
            case R.id.tv_store_name: {
                //TODO store이동
//                Intent intent = new Intent(mContext, );
//                startActivity(intent);
                break;
            }
        }
    }
}
