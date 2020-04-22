package com.rightcode.wellcar.network.requester.storeReview;

import android.content.Context;

import com.rightcode.wellcar.network.requester.AbstractRequester;

import lombok.Setter;
import retrofit2.Call;

public class StoreReviewListRequester extends AbstractRequester {

    @Setter
    private Integer storeId;
    @Setter
    private Boolean realTime;
    @Setter
    private String search;

    public StoreReviewListRequester(Context context) {
        super(context);
    }

    @Override
    protected Call genApi() {
        Call call = networkManager.getApi().storeReviewList(storeId, realTime, search);

        return call;
    }
}
