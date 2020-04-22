package com.rightcode.wellcar.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rightcode.wellcar.Adapter.RecyclerViewAdapter.BuyEstimateRecyclerViewAdapter;
import com.rightcode.wellcar.R;
import com.rightcode.wellcar.network.requester.payment.PaymentEstimateListRequester;
import com.rightcode.wellcar.network.responser.paymentEstimate.PaymentEstimateListResponser;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BuyEstimateFragment extends BaseFragment {

    @BindView(R.id.rv_buy_estimate)
    RecyclerView rv_buy_estimate;

    private View root;
    private BuyEstimateRecyclerViewAdapter mBuyEstimateRecyclerViewAdapter;

    //------------------------------------------------------------------------------------------
    // contructor
    //------------------------------------------------------------------------------------------

    public static BuyEstimateFragment newInstance() {
        BuyEstimateFragment f = new BuyEstimateFragment();
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
        root = inflater.inflate(R.layout.fragment_buy_estimate, container, false);

        ButterKnife.bind(this, root);
        initialize();
        paymentEstimateList();
        return root;
    }

    //----------------------------------------------------------------------------------------------
    // private
    //----------------------------------------------------------------------------------------------

    private void initialize() {
        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rv_buy_estimate.setLayoutManager(verticalLayoutManager);
        mBuyEstimateRecyclerViewAdapter = new BuyEstimateRecyclerViewAdapter(getContext());
        rv_buy_estimate.setAdapter(mBuyEstimateRecyclerViewAdapter);
    }

    private void paymentEstimateList() {
        showLoading();
        PaymentEstimateListRequester paymentEstimateListRequester = new PaymentEstimateListRequester(getContext());

        request(paymentEstimateListRequester,
                success -> {
                    PaymentEstimateListResponser result = (PaymentEstimateListResponser) success;
                    if (result.getCode() == 200) {
                        mBuyEstimateRecyclerViewAdapter.setData(result.getList());
                        mBuyEstimateRecyclerViewAdapter.notifyDataSetChanged();
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
