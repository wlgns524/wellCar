package com.rightcode.wellcar.network.requester.estimateStore;

import android.content.Context;

import com.rightcode.wellcar.network.model.request.estimate.EstimateRegister;
import com.rightcode.wellcar.network.requester.AbstractRequester;

import lombok.Setter;
import retrofit2.Call;

public class EstimateStoreListRequester extends AbstractRequester {

    public EstimateStoreListRequester(Context context) {
        super(context);
    }

    @Override
    protected Call genApi() {
        Call call = networkManager.getApi().estimateStoreList();

        return call;
    }
}
