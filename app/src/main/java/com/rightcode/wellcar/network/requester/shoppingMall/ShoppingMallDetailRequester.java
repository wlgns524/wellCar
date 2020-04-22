package com.rightcode.wellcar.network.requester.shoppingMall;

import android.content.Context;

import com.rightcode.wellcar.network.requester.AbstractRequester;

import lombok.Setter;
import retrofit2.Call;

public class ShoppingMallDetailRequester extends AbstractRequester {

    @Setter
    private Integer shoppingMallId;

    public ShoppingMallDetailRequester(Context context) {
        super(context);
    }

    @Override
    protected Call genApi() {
        Call call = networkManager.getApi().shoppingMallDetail(shoppingMallId);

        return call;
    }
}
