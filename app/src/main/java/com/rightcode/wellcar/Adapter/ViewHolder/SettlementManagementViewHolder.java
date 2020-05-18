package com.rightcode.wellcar.Adapter.ViewHolder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.rightcode.wellcar.R;
import com.rightcode.wellcar.Util.MoneyHelper;
import com.rightcode.wellcar.network.model.response.accountCompany.AccountCompanyList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SettlementManagementViewHolder extends CommonRecyclerViewHolder {

    @BindView(R.id.tv_status)
    TextView tv_status;
    @BindView(R.id.tv_date)
    TextView tv_date;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_value)
    TextView tv_value;

    private Context mContext;
    private AccountCompanyList data;

    public SettlementManagementViewHolder(View viewHolder, Context context) {
        super(viewHolder, context);
        mContext = context;
        ButterKnife.bind(this, itemView);
    }

    public void onBind(AccountCompanyList data) {
        tv_status.setText(data.getStatus());
        tv_date.setText(data.getCreatedAt());
        tv_title.setText(data.getTitle());
        tv_name.setText(data.getName());
        if (data.getStatus().equals("이용권")) {
            tv_status.setBackground(mContext.getDrawable(R.drawable.app_color_border_app_color_background_corner_5));
            tv_value.setText(data.getCount() + "개");
        } else if (data.getStatus().equals("환전")) {
            tv_status.setBackground(mContext.getDrawable(R.drawable.fdae0c_border_fdae0c_background_corner_5));
            tv_value.setText(MoneyHelper.getKorUnit(data.getPrice()));
        } else if (data.getStatus().equals("결제")) {
            tv_status.setBackground(mContext.getDrawable(R.drawable.red_border_red_background_corner_5));
            tv_value.setText(MoneyHelper.getKorUnit(data.getPrice()));
        }
    }
}
