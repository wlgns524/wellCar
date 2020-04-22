package com.rightcode.wellcar.network.requester.estimate;

import android.content.Context;

import com.rightcode.wellcar.network.requester.AbstractRequester;

import lombok.Setter;
import retrofit2.Call;

public class EstimateRemoveRequester extends AbstractRequester {

    @Setter
    private Integer id;

    public EstimateRemoveRequester(Context context) {
        super(context);
    }

    @Override
    protected Call genApi() {
        Call call = networkManager.getApi().estimateRemove(id);

        return call;
    }
}
