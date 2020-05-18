package com.rightcode.wellcar.network.requester.estimateStore;

import android.content.Context;

import com.rightcode.wellcar.network.model.request.estimateStore.EstimateStoreUpdate;
import com.rightcode.wellcar.network.requester.AbstractRequester;

import lombok.Setter;
import retrofit2.Call;

public class EstimateStoreUpdateRequester extends AbstractRequester {

    @Setter
    private Integer id;
    @Setter
    private EstimateStoreUpdate param;

    public EstimateStoreUpdateRequester(Context context) {
        super(context);
    }

    @Override
    protected Call genApi() {
        Call call = networkManager.getApi().estimateStoreUpdate(id, param);

        return call;
    }
}
