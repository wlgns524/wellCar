package com.rightcode.wellcar.network.requester.auth;

import android.content.Context;

import com.rightcode.wellcar.network.requester.AbstractRequester;

import lombok.Setter;
import retrofit2.Call;

public class ExistLoginIdRequester extends AbstractRequester {

    @Setter
    private String loginId;

    public ExistLoginIdRequester(Context context) {
        super(context);
    }

    @Override
    protected Call genApi() {
        Call call = networkManager.getApi().existLoginId(loginId);

        return call;
    }
}
