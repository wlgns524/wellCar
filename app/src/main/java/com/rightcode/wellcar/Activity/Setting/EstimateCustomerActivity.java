package com.rightcode.wellcar.Activity.Setting;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rightcode.wellcar.Activity.BaseActivity;
import com.rightcode.wellcar.Adapter.RecyclerViewAdapter.EstimateCustomerRecyclerViewAdapter;
import com.rightcode.wellcar.Fragment.TopFragment;
import com.rightcode.wellcar.R;
import com.rightcode.wellcar.RxJava.Event;
import com.rightcode.wellcar.RxJava.RxBus;
import com.rightcode.wellcar.RxJava.RxEvent.EstimateRemoveEvent;
import com.rightcode.wellcar.Util.FragmentUtil;
import com.rightcode.wellcar.network.model.CommonResult;
import com.rightcode.wellcar.network.requester.estimate.EstimateListRequester;
import com.rightcode.wellcar.network.requester.estimate.EstimateRemoveRequester;
import com.rightcode.wellcar.network.responser.estimate.EstimateListResponser;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EstimateCustomerActivity extends BaseActivity {

    @Event(EstimateRemoveEvent.class)
    public void onEstimateRemoveEvent(EstimateRemoveEvent event) {
        estimateRemove(event.getId());
    }

    @BindView(R.id.rv_estimate_customer)
    RecyclerView rv_estimate_customer;

    private TopFragment mTopFragment;
    private EstimateCustomerRecyclerViewAdapter mEstimateCustomerRecyclerViewAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estimate_customer);

        RxBus.register(this);
        ButterKnife.bind(this);
        initialize();
        estimateList();
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

        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(EstimateCustomerActivity.this, LinearLayoutManager.VERTICAL, false);
        rv_estimate_customer.setLayoutManager(verticalLayoutManager);
        mEstimateCustomerRecyclerViewAdapter = new EstimateCustomerRecyclerViewAdapter(EstimateCustomerActivity.this);
        rv_estimate_customer.setAdapter(mEstimateCustomerRecyclerViewAdapter);
    }

    private void estimateList() {
        showLoading();
        EstimateListRequester estimateListRequester = new EstimateListRequester(EstimateCustomerActivity.this);

        request(estimateListRequester,
                success -> {
                    EstimateListResponser result = (EstimateListResponser) success;
                    if (result.getCode() == 200) {
                        mEstimateCustomerRecyclerViewAdapter.setData(result.getList());
                        mEstimateCustomerRecyclerViewAdapter.notifyDataSetChanged();
                    } else {
                        showServerErrorDialog(result.getResultMsg());
                    }
                    hideLoading();
                }, fail -> {
                    hideLoading();
                    showServerErrorDialog(fail.getResultMsg());
                });
    }

    private void estimateRemove(Integer id) {
        EstimateRemoveRequester estimateRemoveRequester = new EstimateRemoveRequester(EstimateCustomerActivity.this);
        estimateRemoveRequester.setId(id);

        request(estimateRemoveRequester,
                success -> {
                    CommonResult result = (CommonResult) success;
                    if (result.getCode() == 200) {
                        estimateList();
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
