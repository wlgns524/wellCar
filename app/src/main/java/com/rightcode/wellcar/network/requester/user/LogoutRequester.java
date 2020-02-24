package com.rightcode.wellcar.network.requester.user;

import android.content.Context;

import com.rightcode.wellcar.network.requester.AbstractRequester;

import retrofit2.Call;

public class LogoutRequester extends AbstractRequester {

    public LogoutRequester(Context context) {
        super(context);
    }

    @Override
    protected Call genApi() {
        Call call = networkManager.getApi().logout();

        return call;
    }
}
