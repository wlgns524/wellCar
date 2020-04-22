package com.rightcode.wellcar.network.requester.storeReview;

import android.content.Context;

import com.rightcode.wellcar.network.requester.AbstractRequester;

import lombok.Setter;
import retrofit2.Call;

public class StoreReviewDetailRequester extends AbstractRequester {

    @Setter
    private Integer storeId;

    public StoreReviewDetailRequester(Context context) {
        super(context);
    }

    @Override
    protected Call genApi() {
        Call call = networkManager.getApi().storeReviewDetail(storeId);

        return call;
    }
}
