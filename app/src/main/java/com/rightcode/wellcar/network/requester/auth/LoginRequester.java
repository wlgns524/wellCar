package com.rightcode.wellcar.network.requester.auth;

import android.content.Context;

import com.rightcode.wellcar.Util.Log;
import com.rightcode.wellcar.network.model.request.auth.Login;
import com.rightcode.wellcar.network.requester.AbstractRequester;

import lombok.Setter;
import retrofit2.Call;

public class LoginRequester extends AbstractRequester {

    @Setter
    private Login param;

    public LoginRequester(Context context) {
        super(context);
    }

    @Override
    protected Call genApi() {
        Call call = networkManager.getApi().login(param);

        return call;
    }
}
