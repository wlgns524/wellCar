package com.rightcode.wellcar.network.requester.storeReview;

import android.content.Context;

import com.rightcode.wellcar.network.requester.AbstractRequester;

import lombok.Setter;
import retrofit2.Call;

public class StoreReviewCheckRequester extends AbstractRequester {

    @Setter
    private Integer storeId;

    public StoreReviewCheckRequester(Context context) {
        super(context);
    }

    @Override
    protected Call genApi() {
        Call call = networkManager.getApi().storeReviewCheck(storeId);

        return call;
    }
}
