package com.rightcode.wellcar.network.requester.shoppingMall;

import android.content.Context;

import com.rightcode.wellcar.network.requester.AbstractRequester;

import java.util.ArrayList;

import lombok.Setter;
import retrofit2.Call;

public class ShoppingMallListRequester extends AbstractRequester {

    public ShoppingMallListRequester(Context context) {
        super(context);
    }

    @Override
    protected Call genApi() {
        Call call = networkManager.getApi().shoppingMallList();

        return call;
    }
}
