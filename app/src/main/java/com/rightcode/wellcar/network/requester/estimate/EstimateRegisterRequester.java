package com.rightcode.wellcar.network.requester.estimate;

import android.content.Context;

import com.rightcode.wellcar.network.model.request.estimate.EstimateRegister;
import com.rightcode.wellcar.network.requester.AbstractRequester;

import lombok.Setter;
import retrofit2.Call;

public class EstimateRegisterRequester extends AbstractRequester {

    @Setter
    private EstimateRegister param;

    public EstimateRegisterRequester(Context context) {
        super(context);
    }

    @Override
    protected Call genApi() {
        Call call = networkManager.getApi().estimateRegister(param);

        return call;
    }
}
