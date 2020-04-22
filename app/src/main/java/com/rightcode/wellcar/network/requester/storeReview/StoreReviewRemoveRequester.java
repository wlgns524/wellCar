package com.rightcode.wellcar.network.requester.storeReview;

import android.content.Context;

import com.rightcode.wellcar.network.requester.AbstractRequester;

import lombok.Setter;
import retrofit2.Call;

public class StoreReviewRemoveRequester extends AbstractRequester {

    @Setter
    private Integer reviewId;

    public StoreReviewRemoveRequester(Context context) {
        super(context);
    }

    @Override
    protected Call genApi() {
        Call call = networkManager.getApi().storeReviewRemove(reviewId);

        return call;
    }
}
