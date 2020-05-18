package com.rightcode.wellcar.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rightcode.wellcar.Adapter.RecyclerViewAdapter.CompanyEstimateManagementRecyclerViewAdapter;
import com.rightcode.wellcar.R;
import com.rightcode.wellcar.RxJava.Event;
import com.rightcode.wellcar.RxJava.RxBus;
import com.rightcode.wellcar.RxJava.RxEvent.EstimateUpdateEvent;
import com.rightcode.wellcar.Util.ToastUtil;
import com.rightcode.wellcar.network.model.CommonResult;
import com.rightcode.wellcar.network.model.request.estimateStore.EstimateStoreUpdate;
import com.rightcode.wellcar.network.requester.estimateStore.EstimateStoreListRequester;
import com.rightcode.wellcar.network.requester.estimateStore.EstimateStoreUpdateRequester;
import com.rightcode.wellcar.network.responser.estimate.EstimateStoreListResponser;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CompanyManagementEstimateManagementFragment extends BaseFragment {

    @Event(EstimateUpdateEvent.class)
    public void onEstimateUpdateEvent(EstimateUpdateEvent event) {
        estimateStoreUpdate(event.getId(), event.getPrice());
    }

    @BindView(R.id.rv_company_management_estimate_management)
    RecyclerView rv_company_management_estimate_management;

    private View root;
    private CompanyEstimateManagementRecyclerViewAdapter mCompanyEstimateManagementRecyclerViewAdapter;

    //------------------------------------------------------------------------------------------
    // contructor
    //------------------------------------------------------------------------------------------

    public static CompanyManagementEstimateManagementFragment newInstance() {
        CompanyManagementEstimateManagementFragment f = new CompanyManagementEstimateManagementFragment();
        Bundle b = new Bundle();
        f.setArguments(b);
        return f;
    }

    //----------------------------------------------------------------------------------------------
    // Life Cycle
    //----------------------------------------------------------------------------------------------

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_company_management_estimate_management, container, false);

        RxBus.register(this);
        ButterKnife.bind(this, root);
        initialize();
        estimateStoreList();

        return root;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.unregister(this);
    }

    //----------------------------------------------------------------------------------------------
    // private
    //----------------------------------------------------------------------------------------------

    private void initialize() {
        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rv_company_management_estimate_management.setLayoutManager(verticalLayoutManager);
        mCompanyEstimateManagementRecyclerViewAdapter = new CompanyEstimateManagementRecyclerViewAdapter(getContext());
        rv_company_management_estimate_management.setAdapter(mCompanyEstimateManagementRecyclerViewAdapter);
    }

    private void estimateStoreList() {
        showLoading();
        EstimateStoreListRequester estimateStoreListRequester = new EstimateStoreListRequester(getContext());

        request(estimateStoreListRequester,
                success -> {
                    EstimateStoreListResponser result = (EstimateStoreListResponser) success;
                    if (result.getCode() == 200) {
                        mCompanyEstimateManagementRecyclerViewAdapter.setData(result.getList());
                        mCompanyEstimateManagementRecyclerViewAdapter.notifyDataSetChanged();
                    } else {
                        showServerErrorDialog(result.getResultMsg());
                    }
                    hideLoading();
                }, fail -> {
                    hideLoading();
                    showServerErrorDialog(fail.getResultMsg());
                });
    }

    private void estimateStoreUpdate(Integer id, Integer price) {
        showLoading();
        EstimateStoreUpdateRequester estimateStoreUpdateRequester = new EstimateStoreUpdateRequester(getContext());
        estimateStoreUpdateRequester.setId(id);
        EstimateStoreUpdate param = new EstimateStoreUpdate();
        param.setPrice(price);
        estimateStoreUpdateRequester.setParam(param);

        request(estimateStoreUpdateRequester,
                success -> {
                    CommonResult result = (CommonResult) success;
                    if (result.getCode() == 200) {
                        ToastUtil.show(getContext(), "견적 입력되었습니다");
                        estimateStoreList();
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

