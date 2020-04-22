package com.rightcode.wellcar.network.requester.auth;

import android.content.Context;

import com.rightcode.wellcar.network.model.request.auth.Join;
import com.rightcode.wellcar.network.requester.AbstractRequester;

import lombok.Setter;
import retrofit2.Call;

public class JoinRequester extends AbstractRequester {

    @Setter
    private Join param;

    public JoinRequester(Context context) {
        super(context);
    }

    @Override
    protected Call genApi() {
        Call call = networkManager.getApi().join(param);

        return call;
    }
}
