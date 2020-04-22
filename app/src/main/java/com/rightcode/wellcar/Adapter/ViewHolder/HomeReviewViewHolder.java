package com.rightcode.wellcar.Adapter.ViewHolder;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rightcode.wellcar.Activity.ReviewDetailActivity;
import com.rightcode.wellcar.R;
import com.rightcode.wellcar.network.model.response.storeReview.StoreReview;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.rightcode.wellcar.Util.ExtraData.EXTRA_REVIEW_ID;


public class HomeReviewViewHolder extends CommonRecyclerViewHolder implements View.OnClickListener {


    @BindView(R.id.iv_review_image)
    ImageView iv_review_image;
    @BindView(R.id.tv_review_title)
    TextView tv_review_title;
    @BindView(R.id.tv_review_nickname)
    TextView tv_review_nickname;

    private Context mContext;
    private StoreReview data;

    public HomeReviewViewHolder(View viewHolder, Context context) {
        super(viewHolder, context);
        mContext = context;
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(this);
    }

    public void onBind(StoreReview data) {
        this.data = data;
        Glide.with(mContext)
                .load(data.getStoreReviewImages() == null ? "" : data.getStoreReviewImages().get(0).getName())
                .into(iv_review_image);
        tv_review_title.setText(data.getContent());
        tv_review_nickname.setText(data.getUser().getGeneral().getNickname());
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(mContext, ReviewDetailActivity.class);
        intent.putExtra(EXTRA_REVIEW_ID, data.getId());
        startActivity(intent);
    }
}
