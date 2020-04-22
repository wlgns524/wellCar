package com.rightcode.wellcar.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rightcode.wellcar.Adapter.RecyclerViewAdapter.ReviewImageRecyclerViewAdapter;
import com.rightcode.wellcar.Fragment.TopFragment;
import com.rightcode.wellcar.R;
import com.rightcode.wellcar.Util.FragmentUtil;
import com.rightcode.wellcar.network.model.response.storeReview.StoreReview;
import com.rightcode.wellcar.network.model.response.storeReview.StoreReviewDetail;
import com.rightcode.wellcar.network.requester.storeReview.StoreReviewDetailRequester;
import com.rightcode.wellcar.network.responser.storeReview.StoreReviewDetailResponser;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.rightcode.wellcar.Util.ExtraData.EXTRA_REVIEW_ID;

public class ReviewDetailActivity extends BaseActivity {

    @BindView(R.id.tv_store_name)
    TextView tv_store_name;
    @BindView(R.id.tv_user_nickname)
    TextView tv_user_nickname;
    @BindView(R.id.tv_grade_satisfaction)
    TextView tv_grade_satisfaction;
    @BindView(R.id.tv_grade_kindness)
    TextView tv_grade_kindness;
    @BindView(R.id.tv_grade)
    TextView tv_grade;
    @BindView(R.id.tv_review_date)
    TextView tv_review_date;
    @BindView(R.id.rv_review_image)
    RecyclerView rv_review_image;
    @BindView(R.id.tv_review_content)
    TextView tv_review_content;

    private TopFragment mTopFragment;
    private ReviewImageRecyclerViewAdapter mReviewImageRecyclerViewAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_detail);

        ButterKnife.bind(this);
        initialize();
    }


    //------------------------------------------------------------------------------------------
    // private
    //------------------------------------------------------------------------------------------
    private void initialize() {
        mTopFragment = (TopFragment) FragmentUtil.findFragmentByTag(getSupportFragmentManager(), "TopFragment");
        mTopFragment.setText(TopFragment.Menu.CENTER, "리뷰 게시판");
        mTopFragment.setImagePadding(TopFragment.Menu.CENTER, 10);
        mTopFragment.setImage(TopFragment.Menu.LEFT, R.drawable.arrow_left);
        mTopFragment.setImagePadding(TopFragment.Menu.LEFT, 5);
        mTopFragment.setListener(TopFragment.Menu.LEFT, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishWithAnim();
            }
        });

        if (getIntent() != null) {
            storeReview(getIntent().getIntExtra(EXTRA_REVIEW_ID, -1));

            LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(ReviewDetailActivity.this, LinearLayoutManager.HORIZONTAL, false);
            rv_review_image.setLayoutManager(verticalLayoutManager);
            mReviewImageRecyclerViewAdapter = new ReviewImageRecyclerViewAdapter(ReviewDetailActivity.this);
            rv_review_image.setAdapter(mReviewImageRecyclerViewAdapter);
        }
    }

    private void storeReview(Integer storeId) {
        showLoading();
        StoreReviewDetailRequester storeReviewDetailRequester = new StoreReviewDetailRequester(ReviewDetailActivity.this);
        if (storeId != -1)
            storeReviewDetailRequester.setStoreId(storeId);

        request(storeReviewDetailRequester,
                success -> {
                    StoreReviewDetailResponser result = (StoreReviewDetailResponser) success;
                    if (result.getCode() == 200) {
                        initLayout(result.getData());
                    } else {
                        showServerErrorDialog(result.getResultMsg());
                    }
                    hideLoading();
                }, fail -> {
                    hideLoading();
                    showServerErrorDialog(fail.getResultMsg());
                });
    }

    private void initLayout(StoreReviewDetail data) {
        tv_store_name.setText(data.getStore().getName());
        tv_user_nickname.setText(String.format("%s 회원님", data.getUser().getGeneral().getNickname()));
        tv_grade_satisfaction.setText(data.getSatisfaction().toString());
        tv_grade_kindness.setText(data.getKindness().toString());
        tv_grade.setText(data.getRate().toString());
        tv_review_content.setText(data.getContent());
        tv_review_date.setText(data.getCreatedAt());
        mReviewImageRecyclerViewAdapter.setData(data.getStoreReviewImages());
        mReviewImageRecyclerViewAdapter.notifyDataSetChanged();
    }
}
