package com.rightcode.wellcar.Activity.Setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rightcode.wellcar.Activity.BaseActivity;
import com.rightcode.wellcar.Activity.CompanyDetailActivity;
import com.rightcode.wellcar.Activity.ReviewWriteActivity;
import com.rightcode.wellcar.Adapter.RecyclerViewAdapter.BuyDetailEstimateRecyclerViewAdapter;
import com.rightcode.wellcar.Adapter.RecyclerViewAdapter.BuyEstimateRecyclerViewAdapter;
import com.rightcode.wellcar.Fragment.TopFragment;
import com.rightcode.wellcar.R;
import com.rightcode.wellcar.RxJava.RxBus;
import com.rightcode.wellcar.RxJava.RxEvent.EstimateSeletedEvent;
import com.rightcode.wellcar.Util.DataEnums;
import com.rightcode.wellcar.Util.FragmentUtil;
import com.rightcode.wellcar.Util.Log;
import com.rightcode.wellcar.Util.ToastUtil;
import com.rightcode.wellcar.network.model.response.paymentEstimate.PaymentEstimateDetail;
import com.rightcode.wellcar.network.requester.payment.PaymentEstimateDetailRequester;
import com.rightcode.wellcar.network.responser.paymentEstimate.PaymentEstimateDetailResponser;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.rightcode.wellcar.Util.ExtraData.EXTRA_ACTIVITY_ACTION;
import static com.rightcode.wellcar.Util.ExtraData.EXTRA_ACTIVITY_COMPLETE;
import static com.rightcode.wellcar.Util.ExtraData.EXTRA_COMPANY_DETAIL_TYPE;
import static com.rightcode.wellcar.Util.ExtraData.EXTRA_COMPANY_ID;
import static com.rightcode.wellcar.Util.ExtraData.EXTRA_ESTIMATE_ID;
import static com.rightcode.wellcar.Util.ExtraData.EXTRA_PAYMENT_ID;
import static com.rightcode.wellcar.Util.ExtraData.EXTRA_REVIEW_IS_MINE;

public class BuyDetailActivity extends BaseActivity {

    @BindView(R.id.rv_buy_detail)
    RecyclerView rv_buy_detail;

    private TopFragment mTopFragment;
    private BuyDetailEstimateRecyclerViewAdapter mBuyDetailEstimateRecyclerViewAdapter;
    private PaymentEstimateDetail data;
    private Integer estimateId;
    private Boolean isReviewMine;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_detail);

        ButterKnife.bind(this);
        initialize();
    }
//------------------------------------------------------------------------------------------
    // Override
    //------------------------------------------------------------------------------------------

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case EXTRA_ACTIVITY_ACTION: {
                    isReviewMine = true;
                    break;
                }
                default:
                    break;
            }
        }
    }

    @OnClick({R.id.tv_review_write, R.id.tv_store_detail})
    void onClickMenu(View view) {
        switch (view.getId()) {
            case R.id.tv_review_write: {
                if (isReviewMine) {
                    ToastUtil.show(BuyDetailActivity.this, "이미 작성한 리뷰가 있습니다");
                } else {
                    Intent intent = new Intent(BuyDetailActivity.this, ReviewWriteActivity.class);
                    intent.putExtra(EXTRA_COMPANY_ID, data.getEstimateStore().getStore().getId());
                    intent.putExtra(EXTRA_ESTIMATE_ID, estimateId);
                    startActivityForResult(intent, EXTRA_ACTIVITY_ACTION);
                }
                break;
            }
            case R.id.tv_store_detail: {
                Intent intent = new Intent(BuyDetailActivity.this, CompanyDetailActivity.class);
                intent.putExtra(EXTRA_COMPANY_ID, data.getId());
                intent.putExtra(EXTRA_COMPANY_DETAIL_TYPE, DataEnums.CompanyDetailType.BASIC);
                startActivity(intent);
                break;
            }
        }
    }

    //------------------------------------------------------------------------------------------
    // private
    //------------------------------------------------------------------------------------------
    private void initialize() {
        mTopFragment = (TopFragment) FragmentUtil.findFragmentByTag(getSupportFragmentManager(), "TopFragment");
        mTopFragment.setText(TopFragment.Menu.CENTER, "견적구매");
        mTopFragment.setImagePadding(TopFragment.Menu.CENTER, 10);
        mTopFragment.setImage(TopFragment.Menu.LEFT, R.drawable.arrow_left);
        mTopFragment.setImagePadding(TopFragment.Menu.LEFT, 5);
        mTopFragment.setListener(TopFragment.Menu.LEFT, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishWithAnim();
            }
        });


        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(BuyDetailActivity.this, LinearLayoutManager.VERTICAL, false);
        rv_buy_detail.setLayoutManager(verticalLayoutManager);
        mBuyDetailEstimateRecyclerViewAdapter = new BuyDetailEstimateRecyclerViewAdapter(BuyDetailActivity.this);
        rv_buy_detail.setAdapter(mBuyDetailEstimateRecyclerViewAdapter);

        if (getIntent() != null) {
            paymentEstimateDetail(getIntent().getIntExtra(EXTRA_PAYMENT_ID, -1));
            estimateId = getIntent().getIntExtra(EXTRA_ESTIMATE_ID, -1);
            isReviewMine = getIntent().getBooleanExtra(EXTRA_REVIEW_IS_MINE, false);
        }
    }

    private void paymentEstimateDetail(Integer paymentId) {
        showLoading();
        PaymentEstimateDetailRequester paymentEstimateDetailRequester = new PaymentEstimateDetailRequester(BuyDetailActivity.this);
        paymentEstimateDetailRequester.setPaymentId(paymentId);

        request(paymentEstimateDetailRequester,
                success -> {
                    PaymentEstimateDetailResponser result = (PaymentEstimateDetailResponser) success;
                    if (result.getCode() == 200) {
                        data = result.getData();
                        mBuyDetailEstimateRecyclerViewAdapter.setData(result.getData());
                        mBuyDetailEstimateRecyclerViewAdapter.notifyDataSetChanged();
                    } else {
                        showServerErrorDialog(result.getResultMsg());
                    }
                    hideLoading();
                }, fail -> {
                    hideLoading();
                    showServerErrorDialog(fail.getResultMsg());
                });
    }
}
