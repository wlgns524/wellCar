package com.rightcode.wellcar.network.requester.storeReviewUser;

import android.content.Context;

import com.rightcode.wellcar.network.requester.AbstractRequester;

import lombok.Setter;
import retrofit2.Call;

public class StoreReviewUserListRequester extends AbstractRequester {

    public StoreReviewUserListRequester(Context context) {
        super(context);
    }

    @Override
    protected Call genApi() {
        Call call = networkManager.getApi().storeReviewUserList();

        return call;
    }
}
