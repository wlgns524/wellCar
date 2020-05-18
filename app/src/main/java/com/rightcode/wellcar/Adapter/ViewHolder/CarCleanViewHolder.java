package com.rightcode.wellcar.Adapter.ViewHolder;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rightcode.wellcar.Activity.CompanyDetailActivity;
import com.rightcode.wellcar.Activity.ReviewWriteActivity;
import com.rightcode.wellcar.R;
import com.rightcode.wellcar.Util.DataEnums;
import com.rightcode.wellcar.Util.ToastUtil;
import com.rightcode.wellcar.network.model.response.store.Store;
import com.rightcode.wellcar.network.model.response.ticketHistory.StoreReviewManagement;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.rightcode.wellcar.Util.ExtraData.EXTRA_COMPANY_DETAIL_TYPE;
import static com.rightcode.wellcar.Util.ExtraData.EXTRA_COMPANY_ID;
import static com.rightcode.wellcar.Util.ExtraData.EXTRA_TICKET_HISTORY_ID;


public class CarCleanViewHolder extends CommonRecyclerViewHolder implements View.OnClickListener {

    @BindView(R.id.iv_company_image)
    ImageView iv_company_image;
    @BindView(R.id.tv_company_name)
    TextView tv_company_name;
    @BindView(R.id.tv_grade)
    TextView tv_grade;
    @BindView(R.id.tv_introduction)
    TextView tv_introduction;
    @BindView(R.id.tv_company_review_count)
    TextView tv_company_review_count;
    @BindView(R.id.tv_review_write)
    TextView tv_review_write;

    private Context mContext;
    private StoreReviewManagement data;

    public CarCleanViewHolder(View viewHolder, Context context) {
        super(viewHolder, context);
        mContext = context;
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(this);
    }

    public void onBind(StoreReviewManagement data) {
        this.data = data;
        initLayout();
    }

    private void initLayout() {
        Glide.with(mContext)
                .load(data.getThumbnail())
                .into(iv_company_image);
        tv_company_name.setText(data.getName());
        tv_grade.setText(data.getRate().toString());
        tv_introduction.setText(data.getIntroduction());
        tv_company_review_count.setText(data.getReviewCount().toString());
        if (data.getIsReviewMine()) {
            tv_review_write.setTextColor(mContext.getColor(R.color.black));
            tv_review_write.setBackground(mContext.getDrawable(R.drawable.space_border_space_corner_5));
        } else {
            tv_review_write.setTextColor(mContext.getColor(R.color.white));
            tv_review_write.setBackground(mContext.getDrawable(R.drawable.fdae0c_border_fdae0c_background_corner_5));
        }
    }

    @Override
    public void onClick(View v) {
        if (data.getIsReviewMine()) {
            ToastUtil.show(mContext, "이미 작성한 리뷰가 있습니다");
        } else {
            Intent intent = new Intent(mContext, ReviewWriteActivity.class);
            intent.putExtra(EXTRA_COMPANY_ID, data.getId());
            intent.putExtra(EXTRA_TICKET_HISTORY_ID, data.getTicketHistoryId());
            startActivity(intent);
        }
    }
}
