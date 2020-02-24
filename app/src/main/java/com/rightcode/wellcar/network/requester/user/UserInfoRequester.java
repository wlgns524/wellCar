package com.rightcode.wellcar.network.requester.user;

import android.content.Context;

import com.rightcode.wellcar.network.requester.AbstractRequester;

import retrofit2.Call;

public class UserInfoRequester extends AbstractRequester {

    public UserInfoRequester(Context context) {
        super(context);
    }

    @Override
    protected Call genApi() {
        Call call = networkManager.getApi().userInfo();

        return call;
    }
}
