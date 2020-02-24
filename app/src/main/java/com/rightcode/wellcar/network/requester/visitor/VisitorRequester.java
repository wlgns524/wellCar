package com.rightcode.wellcar.network.requester.visitor;

import android.content.Context;


import com.rightcode.wellcar.network.requester.AbstractRequester;

import retrofit2.Call;

public class VisitorRequester extends AbstractRequester {

    public VisitorRequester(Context context) {
        super(context);
    }

    @Override
    protected Call genApi() {
        Call call = networkManager.getApi().visitor();

        return call;
    }
}
