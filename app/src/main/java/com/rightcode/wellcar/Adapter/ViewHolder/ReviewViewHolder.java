package com.rightcode.wellcar.Adapter.ViewHolder;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rightcode.wellcar.Activity.ReviewDetailActivity;
import com.rightcode.wellcar.Adapter.RecyclerViewAdapter.ReviewImageRecyclerViewAdapter;
import com.rightcode.wellcar.R;
import com.rightcode.wellcar.network.model.response.storeReview.StoreReview;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.rightcode.wellcar.Util.ExtraData.EXTRA_REVIEW_DATA;
import static com.rightcode.wellcar.Util.ExtraData.EXTRA_REVIEW_ID;


public class ReviewViewHolder extends CommonRecyclerViewHolder implements View.OnClickListener {

    @BindView(R.id.tv_store_name)
    TextView tv_store_name;
    @BindView(R.id.tv_user_nickname)
    TextView tv_user_nickname;
    @BindView(R.id.tv_grade)
    TextView tv_grade;
    @BindView(R.id.rv_review_image)
    RecyclerView rv_review_image;
    @BindView(R.id.tv_review_content)
    TextView tv_review_content;
    @BindView(R.id.tv_review_date)
    TextView tv_review_date;

    private Context mContext;
    private ReviewImageRecyclerViewAdapter mReviewImageRecyclerViewAdapter;
    private StoreReview data;

    public ReviewViewHolder(View viewHolder, Context context) {
        super(viewHolder, context);
        mContext = context;
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(this);
    }

    public void onBind(StoreReview data) {
        this.data = data;
//        tv_store_name.setText(data.getStore().getName());
        tv_user_nickname.setText(String.format("%s 회원님", data.getUser().getGeneral().getNickname()));
        tv_grade.setText(data.getRate().toString());
        tv_review_content.setText(data.getContent());
        tv_review_date.setText(data.getCreatedAt());

        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        rv_review_image.setLayoutManager(verticalLayoutManager);
        mReviewImageRecyclerViewAdapter = new ReviewImageRecyclerViewAdapter(mContext);
        mReviewImageRecyclerViewAdapter.setData(data.getStoreReviewImages());
        rv_review_image.setAdapter(mReviewImageRecyclerViewAdapter);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(mContext, ReviewDetailActivity.class);
        intent.putExtra(EXTRA_REVIEW_ID, data.getId());
        startActivity(intent);
    }
}
