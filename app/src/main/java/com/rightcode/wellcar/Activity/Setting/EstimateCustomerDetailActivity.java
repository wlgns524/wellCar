package com.rightcode.wellcar.Activity.Setting;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rightcode.wellcar.Activity.BaseActivity;
import com.rightcode.wellcar.Adapter.RecyclerViewAdapter.EstimateCustomerDetailRecyclerViewAdapter;
import com.rightcode.wellcar.Fragment.BottomFragment;
import com.rightcode.wellcar.Fragment.TopFragment;
import com.rightcode.wellcar.R;
import com.rightcode.wellcar.RxJava.Event;
import com.rightcode.wellcar.RxJava.RxBus;
import com.rightcode.wellcar.RxJava.RxEvent.EstimateCompleteEvent;
import com.rightcode.wellcar.RxJava.RxEvent.EstimateSeletedEvent;
import com.rightcode.wellcar.RxJava.RxEvent.MoveTalkEvent;
import com.rightcode.wellcar.Util.FragmentUtil;
import com.rightcode.wellcar.Util.ToastUtil;
import com.rightcode.wellcar.network.model.CommonResult;
import com.rightcode.wellcar.network.model.request.payment.PaymentUpdate;
import com.rightcode.wellcar.network.requester.estimate.EstimateDetailRequester;
import com.rightcode.wellcar.network.requester.payment.PaymentUpdateRequester;
import com.rightcode.wellcar.network.responser.CommonResponser;
import com.rightcode.wellcar.network.responser.estimate.EstimateDetailResponser;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.rightcode.wellcar.Util.ExtraData.EXTRA_ESTIMATE_ID;

public class EstimateCustomerDetailActivity extends BaseActivity {

    @Event(MoveTalkEvent.class)
    public void onMoveTalkEvent(MoveTalkEvent event) {
        finishWithAnim();
    }

    @Event(EstimateSeletedEvent.class)
    public void onEstimateSeletedEvent(EstimateSeletedEvent event) {
        estimateList(event.getId());
    }

    @Event(EstimateCompleteEvent.class)
    public void onEstimateCompleteEvent(EstimateCompleteEvent event) {
        updateEstimate(event.getEstimateId(), event.getPaymentId());
    }
    @BindView(R.id.rv_estimate_customer_detail)
    RecyclerView rv_estimate_customer_detail;

    private TopFragment mTopFragment;
    private EstimateCustomerDetailRecyclerViewAdapter mEstimateCustomerDetailRecyclerViewAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estimate_customer_detail);

        RxBus.register(this);
        ButterKnife.bind(this);
        initialize();
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
        mTopFragment.setText(TopFragment.Menu.CENTER, "견적 관리");
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
            estimateList(getIntent().getIntExtra(EXTRA_ESTIMATE_ID, 0));
        }

        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(EstimateCustomerDetailActivity.this, LinearLayoutManager.VERTICAL, false);
        rv_estimate_customer_detail.setLayoutManager(verticalLayoutManager);
        mEstimateCustomerDetailRecyclerViewAdapter = new EstimateCustomerDetailRecyclerViewAdapter(EstimateCustomerDetailActivity.this);
        rv_estimate_customer_detail.setAdapter(mEstimateCustomerDetailRecyclerViewAdapter);
    }

    private void updateEstimate(Integer estimateId, Integer paymentId) {
        PaymentUpdate update = new PaymentUpdate();
        update.setIsConstruction(true);


        PaymentUpdateRequester requester = new PaymentUpdateRequester(EstimateCustomerDetailActivity.this);
        requester.setPaymentUpdate(update);
        requester.setPaymentId(paymentId);

        request(requester, success -> {
            CommonResult result = success;
            if (result.getCode() == 200) {
                ToastUtil.show(this, "시공 완료 처리되었습니다.");
                estimateList(estimateId);
            } else {
                showServerErrorDialog(result.getResultMsg());
            }
        }, fail -> {
            if (!handleServerError(fail)) {
                showServerErrorDialog(fail.getResultMsg());
            }
        });
    }

    private void estimateList(Integer id) {
        showLoading();
        EstimateDetailRequester estimateDetailRequester = new EstimateDetailRequester(EstimateCustomerDetailActivity.this);
        estimateDetailRequester.setId(id);
        request(estimateDetailRequester,
                success -> {
                    EstimateDetailResponser result = (EstimateDetailResponser) success;
                    if (result.getCode() == 200) {
                        mEstimateCustomerDetailRecyclerViewAdapter.setData(result.getData());
                        mEstimateCustomerDetailRecyclerViewAdapter.notifyDataSetChanged();
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
