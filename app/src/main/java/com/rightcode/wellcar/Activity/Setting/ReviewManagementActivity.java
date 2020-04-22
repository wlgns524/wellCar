package com.rightcode.wellcar.Activity.Setting;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rightcode.wellcar.Activity.BaseActivity;
import com.rightcode.wellcar.Adapter.RecyclerViewAdapter.ReviewManagementRecyclerViewAdapter;
import com.rightcode.wellcar.Fragment.TopFragment;
import com.rightcode.wellcar.R;
import com.rightcode.wellcar.RxJava.Event;
import com.rightcode.wellcar.RxJava.RxBus;
import com.rightcode.wellcar.RxJava.RxEvent.EstimateRemoveEvent;
import com.rightcode.wellcar.RxJava.RxEvent.ReviewRemoveEvent;
import com.rightcode.wellcar.Util.FragmentUtil;
import com.rightcode.wellcar.network.model.CommonResult;
import com.rightcode.wellcar.network.requester.storeReview.StoreReviewRemoveRequester;
import com.rightcode.wellcar.network.requester.storeReviewUser.StoreReviewUserListRequester;
import com.rightcode.wellcar.network.responser.storeReview.StoreReviewListResponser;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReviewManagementActivity extends BaseActivity {

    @Event(ReviewRemoveEvent.class)
    public void onReviewRemoveEvent(ReviewRemoveEvent event) {
        storeReviewRemove(event.getId());
    }

    @BindView(R.id.rv_review_management)
    RecyclerView rv_review_management;

    private TopFragment mTopFragment;
    private ReviewManagementRecyclerViewAdapter mReviewManagementRecyclerViewAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_management);

        RxBus.register(this);
        ButterKnife.bind(this);
        initialize();
        storeReviewUserList();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.unregister(this);
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

        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(ReviewManagementActivity.this, LinearLayoutManager.VERTICAL, false);
        rv_review_management.setLayoutManager(verticalLayoutManager);
        mReviewManagementRecyclerViewAdapter = new ReviewManagementRecyclerViewAdapter(ReviewManagementActivity.this);
        rv_review_management.setAdapter(mReviewManagementRecyclerViewAdapter);
    }


    private void storeReviewUserList() {
        showLoading();
        StoreReviewUserListRequester storeReviewUserListRequester = new StoreReviewUserListRequester(ReviewManagementActivity.this);

        request(storeReviewUserListRequester,
                success -> {
                    StoreReviewListResponser result = (StoreReviewListResponser) success;
                    if (result.getCode() == 200) {
                        mReviewManagementRecyclerViewAdapter.setData(result.getList());
                        mReviewManagementRecyclerViewAdapter.notifyDataSetChanged();
                    } else {
                        showServerErrorDialog(result.getResultMsg(), action -> {
                            finishWithAnim();
                        });
                    }
                    hideLoading();
                }, fail -> {
                    hideLoading();
                    showServerErrorDialog(fail.getResultMsg());
                });
    }

    private void storeReviewRemove(Integer reviewId) {
        showLoading();
        StoreReviewRemoveRequester storeReviewRemoveRequester = new StoreReviewRemoveRequester(ReviewManagementActivity.this);
        storeReviewRemoveRequester.setReviewId(reviewId);

        request(storeReviewRemoveRequester,
                success -> {
                    CommonResult result = (CommonResult) success;
                    if (result.getCode() == 200) {
                        storeReviewUserList();
                    } else {
                        showServerErrorDialog(result.getResultMsg(), action -> {
                            finishWithAnim();
                        });
                    }
                    hideLoading();
                }, fail -> {
                    hideLoading();
                    showServerErrorDialog(fail.getResultMsg());
                });

    }
}
