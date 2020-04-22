package com.rightcode.wellcar.network.requester.store;

import android.content.Context;

import com.rightcode.wellcar.network.requester.AbstractRequester;

import lombok.Setter;
import retrofit2.Call;

public class StoreDetailRequester extends AbstractRequester {

    @Setter
    private Integer storeId;

    public StoreDetailRequester(Context context) {
        super(context);
    }

    @Override
    protected Call genApi() {
        Call call = networkManager.getApi().storeDetail(storeId);

        return call;
    }
}
