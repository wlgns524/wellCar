package com.rightcode.wellcar.Activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.textservice.TextInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rightcode.wellcar.Adapter.RecyclerViewAdapter.ReviewRecyclerViewAdapter;
import com.rightcode.wellcar.Fragment.TopFragment;
import com.rightcode.wellcar.R;
import com.rightcode.wellcar.Util.CommonUtil;
import com.rightcode.wellcar.Util.FragmentUtil;
import com.rightcode.wellcar.Util.Log;
import com.rightcode.wellcar.Util.ToastUtil;
import com.rightcode.wellcar.network.model.response.storeReview.StoreReview;
import com.rightcode.wellcar.network.requester.storeReview.StoreReviewListRequester;
import com.rightcode.wellcar.network.responser.storeReview.StoreReviewListResponser;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.rightcode.wellcar.Util.ExtraData.EXTRA_COMPANY_ID;

public class ReviewActivity extends BaseActivity {

    @BindView(R.id.ll_search)
    LinearLayout ll_search;
    @BindView(R.id.et_search)
    EditText et_search;
    @BindView(R.id.rv_review)
    RecyclerView rv_review;
    @BindView(R.id.iv_review_refresh)
    ImageView iv_review_refresh;

    private TopFragment mTopFragment;
    private ReviewRecyclerViewAdapter mReviewRecyclerViewAdapter;
    private ArrayList<StoreReview> storeReviews;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        ButterKnife.bind(this);
        initialize();
    }

    @OnClick({R.id.iv_review_refresh, R.id.iv_search})
    void onClickMenu(View view) {
        switch (view.getId()) {
            case R.id.iv_search: {
                if (TextUtils.isEmpty(et_search.getText().toString())) {
                    ToastUtil.show(ReviewActivity.this, "검색어를 입력해주세요");
                    break;
                }
                listFilter();
                CommonUtil.hideKeyPad(rv_review);
                break;
            }
            case R.id.iv_review_refresh: {
                iv_review_refresh.setVisibility(View.GONE);
                et_search.setText("");
                mReviewRecyclerViewAdapter.setData(storeReviews);
                mReviewRecyclerViewAdapter.notifyDataSetChanged();
                break;
            }
        }
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
            Integer companyId = getIntent().getIntExtra(EXTRA_COMPANY_ID, -1);
            storeReview(companyId);
            if (companyId == -1) {
                ll_search.setVisibility(View.VISIBLE);
            } else {
                ll_search.setVisibility(View.GONE);
            }
        }

        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(ReviewActivity.this, LinearLayoutManager.VERTICAL, false);
        rv_review.setLayoutManager(verticalLayoutManager);
        mReviewRecyclerViewAdapter = new ReviewRecyclerViewAdapter(ReviewActivity.this);
        rv_review.setAdapter(mReviewRecyclerViewAdapter);
        rv_review.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                CommonUtil.hideKeyPad(rv_review);
            }
        });
    }

    private void storeReview(Integer storeId) {
        showLoading();
        StoreReviewListRequester storeReviewListRequester = new StoreReviewListRequester(ReviewActivity.this);
        if (storeId != -1)
            storeReviewListRequester.setStoreId(storeId);
        storeReviewListRequester.setRealTime(false);

        request(storeReviewListRequester,
                success -> {
                    StoreReviewListResponser result = (StoreReviewListResponser) success;
                    if (result.getCode() == 200) {
                        storeReviews = result.getList();
                        mReviewRecyclerViewAdapter.setData(result.getList());
                        mReviewRecyclerViewAdapter.notifyDataSetChanged();
                    } else {
                        showServerErrorDialog(result.getResultMsg());
                    }
                    hideLoading();
                }, fail -> {
                    hideLoading();
                    showServerErrorDialog(fail.getResultMsg());
                });
    }

    private void listFilter() {
        ArrayList<StoreReview> reviews = new ArrayList<>();
        for (StoreReview storeReview : storeReviews) {
            if (storeReview.getContent().contains(et_search.getText().toString())) {
                reviews.add(storeReview);
            }
        }
        mReviewRecyclerViewAdapter.setData(reviews);
        mReviewRecyclerViewAdapter.notifyDataSetChanged();
        iv_review_refresh.setVisibility(View.VISIBLE);
    }
}
