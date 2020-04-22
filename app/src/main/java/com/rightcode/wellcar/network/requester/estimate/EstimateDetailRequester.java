package com.rightcode.wellcar.network.requester.estimate;

import android.content.Context;
import android.os.Parcelable;

import com.rightcode.wellcar.network.requester.AbstractRequester;

import lombok.Setter;
import retrofit2.Call;

public class EstimateDetailRequester extends AbstractRequester {

    @Setter
    private Integer id;

    public EstimateDetailRequester(Context context) {
        super(context);
    }

    @Override
    protected Call genApi() {
        Call call = networkManager.getApi().estimateDetail(id);

        return call;
    }
}
