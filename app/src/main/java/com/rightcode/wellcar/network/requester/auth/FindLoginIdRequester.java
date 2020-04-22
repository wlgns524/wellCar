package com.rightcode.wellcar.network.requester.auth;

import android.content.Context;

import com.rightcode.wellcar.network.requester.AbstractRequester;

import lombok.Setter;
import retrofit2.Call;

public class FindLoginIdRequester extends AbstractRequester {

    @Setter
    private String tel;

    public FindLoginIdRequester(Context context) {
        super(context);
    }

    @Override
    protected Call genApi() {
        Call call = networkManager.getApi().findLoginId(tel);

        return call;
    }
}
