package com.rightcode.wellcar.network.requester.storeReview;

import android.content.Context;

import com.rightcode.wellcar.network.model.request.storeReview.StoreReviewRegister;
import com.rightcode.wellcar.network.requester.AbstractRequester;

import lombok.Setter;
import retrofit2.Call;

public class StoreReviewRegisterRequester extends AbstractRequester {

    @Setter
    private Integer storeId;
    @Setter
    private StoreReviewRegister param;

    public StoreReviewRegisterRequester(Context context) {
        super(context);
    }

    @Override
    protected Call genApi() {
        Call call = networkManager.getApi().storeReviewRegister(storeId, param);

        return call;
    }
}
