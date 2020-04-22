package com.rightcode.wellcar.network.requester.auth;

import android.content.Context;

import com.rightcode.wellcar.network.model.request.auth.PasswordChange;
import com.rightcode.wellcar.network.requester.AbstractRequester;

import lombok.Setter;
import retrofit2.Call;

public class PasswordChangeRequester extends AbstractRequester {

    @Setter
    private PasswordChange param;

    public PasswordChangeRequester(Context context) {
        super(context);
    }

    @Override
    protected Call genApi() {
        Call call = networkManager.getApi().passwordChange(param);

        return call;
    }
}
