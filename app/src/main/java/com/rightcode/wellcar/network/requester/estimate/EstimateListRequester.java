package com.rightcode.wellcar.network.requester.estimate;

import android.content.Context;

import com.rightcode.wellcar.Util.DataEnums;
import com.rightcode.wellcar.network.requester.AbstractRequester;

import lombok.Setter;
import retrofit2.Call;

public class EstimateListRequester extends AbstractRequester {

    public EstimateListRequester(Context context) {
        super(context);
    }

    @Override
    protected Call genApi() {
        Call call = networkManager.getApi().estimateList();

        return call;
    }
}
