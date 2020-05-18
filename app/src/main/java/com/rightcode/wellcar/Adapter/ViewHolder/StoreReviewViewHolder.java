package com.rightcode.wellcar.Adapter.ViewHolder;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.rightcode.wellcar.Activity.ReviewDetailActivity;
import com.rightcode.wellcar.Adapter.RecyclerViewAdapter.ReviewImageRecyclerViewAdapter;
import com.rightcode.wellcar.R;
import com.rightcode.wellcar.Util.Log;
import com.rightcode.wellcar.network.model.response.storeReview.StoreReview;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.rightcode.wellcar.Util.ExtraData.EXTRA_REVIEW_ID;


public class StoreReviewViewHolder extends CommonRecyclerViewHolder implements View.OnClickListener {

    @BindView(R.id.iv_review_image)
    ImageView iv_review_image;
    @BindView(R.id.tv_user_nickname)
    TextView tv_user_nickname;
    @BindView(R.id.tv_review_date)
    TextView tv_review_date;
    @BindView(R.id.tv_review_content)
    TextView tv_review_content;

    private Context mContext;
    private StoreReview data;

    public StoreReviewViewHolder(View viewHolder, Context context) {
        super(viewHolder, context);
        mContext = context;
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(this);
    }

    public void onBind(StoreReview data) {
        this.data = data;
        Glide.with(mContext)
                .load(data.getStoreReviewImages() != null && data.getStoreReviewImages().size() > 0 ? data.getStoreReviewImages().get(0).getName() : "")
                .apply(new RequestOptions().transforms(new CenterCrop(), new RoundedCorners(16)))
                .into(iv_review_image);
        tv_user_nickname.setText(String.format("%s 회원님", data.getUser().getGeneral().getNickname()));
        tv_review_content.setText(data.getContent());
        tv_review_date.setText(data.getCreatedAt());

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(mContext, ReviewDetailActivity.class);
        intent.putExtra(EXTRA_REVIEW_ID, data.getId());
        startActivity(intent);
    }
}
